package com.example.binanceAnalyzer;

import com.example.binanceAnalyzer.Controllers.Rest.AuthenticationController;
import com.example.binanceAnalyzer.Controllers.Ws.StatsWsController;
import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import com.example.binanceAnalyzer.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceAnalyzer.Models.Plain.Roles;
import com.example.binanceAnalyzer.Services.UserService;
import com.example.binanceAnalyzer.configs.WebSocketConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@Log4j2
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class WebSocketTests {

    public static final String AUTHORIZATION = "Authorization";
    @Value("${local.server.port}")
    private int port;

    private static WebClient client;
    String symbol="BTCUSDT";
    @Autowired
    ObjectMapper mapper;

    public static String LOCALHOST;
    @Autowired
    UserService userService;
    User userToSave;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AuthenticationProvider authenticationProvider;
    @BeforeAll
    public void setup() throws Exception {

        userToSave=User.builder()
                .email(MVCAuthenticationTests.EMAIL)
                .name(MVCAuthenticationTests.NAME)
                .pass(MVCAuthenticationTests.PASS)
                .userSymbolSubscriptions(new HashSet<>())
                .userProperties(UserPropertiesEntity.builder().role(Roles.RAW_USER.getValue()).build())
                .build();

        userService.save(userToSave);

        LOCALHOST="http://127.0.0.1:"+port;

       MockHttpServletResponse response= mockMvc.perform(MockMvcRequestBuilders.post(LOCALHOST+AuthenticationController.SIGN_IN_DESTINATION)
                        .with(csrf())
                        .param("email",MVCAuthenticationTests.EMAIL)
                        .param("pass",MVCAuthenticationTests.PASS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();


        RunStopFrameHandler runStopFrameHandler = new RunStopFrameHandler(new CompletableFuture<>());

        String wsUrl = "ws://127.0.0.1:" + port + WebSocketConfig.REGISTRY;

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));

        String authenticationHeader=response.getHeader(AUTHORIZATION);

        log.info(authenticationHeader);

        WebSocketHttpHeaders headers=new WebSocketHttpHeaders();

        headers.add(AUTHORIZATION,authenticationHeader);

        StompSession stompSession = stompClient
                .connect(wsUrl,headers, new StompSessionHandlerAdapter() {})
                .get(5, TimeUnit.SECONDS);

        Thread.sleep(5000);

        client = WebClient.builder()
                .stompClient(stompClient)
                .stompSession(stompSession)
                .handler(runStopFrameHandler)
                .build();

    }


    @AfterAll
    public void tearDown() {

        if (client.getStompSession().isConnected()) {
            client.getStompSession().disconnect();
            client.getStompClient().stop();
        }
    }

    //test scheduling with calculate method
    @SneakyThrows
    @Test
    public void tickerStatsSchedulingTest() {

         RunStopFrameHandler handler=client.getHandler();

         StompSession stompSession= client.getStompSession();

         stompSession.subscribe(StatsWsController.FETCH_TICKER_STATS,handler);

         Thread.sleep(1000);

         byte[] payload= (byte[]) handler.getFuture().get();

        Set<LinkedHashMap<String, Object>> params=(Set<LinkedHashMap<String, Object>>)mapper.readValue(payload, Set.class);

        params.stream().forEach(System.out::println);
    }

@Test
@SneakyThrows
public void smaSchedulingTest(){
    RunStopFrameHandler handler=client.getHandler();

    StompSession stompSession= client.getStompSession();

    stompSession.subscribe(StatsWsController.FETCH_MA_STATS,handler);

    Thread.sleep(5000);

    byte[] payload= (byte[]) handler.getFuture().get();

    Set<LinkedHashMap<String, Object>> params=(Set<LinkedHashMap<String, Object>>)mapper.readValue(payload, Set.class);

    params.stream().forEach(System.out::println);
    }


    private List<Transport> createTransportClient() {

        List<Transport> transports = new ArrayList<>(1);

        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        return transports;
    }

    @Data
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class RunStopFrameHandler implements StompFrameHandler {

        CompletableFuture<Object> future;

        @Override
        public @NonNull Type getPayloadType(StompHeaders stompHeaders) {

            //log.info(stompHeaders.toString());

            return byte[].class;
        }

        @Override
        public void handleFrame(@NonNull StompHeaders stompHeaders, Object o) {

            log.info(stompHeaders.toString());

            System.out.println(Strings.repeat("-",100));

            log.info(stompHeaders.getContentType());

            future.complete(o);

            future = new CompletableFuture<>();
        }
    }

    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class WebClient {

        WebSocketStompClient stompClient;

        StompSession stompSession;

        String sessionToken;

        RunStopFrameHandler handler;
    }
}
