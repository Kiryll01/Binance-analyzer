package com.example.binanceAnalyzer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.example.binanceAnalyzer.Controllers.Rest.AuthenticationController;
import com.example.binanceAnalyzer.Models.Dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsocketClient {
    public static ObjectMapper mapper=new ObjectMapper();
    public static final String LOCALHOST="http://localhost:8080";
    public static void main(String[] args){
        // 1.login to rest service
         String authenticationHeader = authToRest();
        // 2.establish websocket connection
        openConnection(authenticationHeader);
    }


    public static String authToRest()  {
        RestTemplate restTemplate = new RestTemplate();
        UserDto userDto= UserDto.builder()
                .email("hamilka540@gmail.com")
                .name("Kiryll")
                .pass("pass")
                .build();
        HttpEntity<UserDto> signUpRequest = new HttpEntity<>(userDto);

      ResponseEntity<String> signUpResponse = restTemplate.exchange(LOCALHOST+AuthenticationController.SIGN_UP_DESTINATION,HttpMethod.POST,signUpRequest, String.class);

        System.out.println(signUpResponse.getBody());

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", "hamilka540@gmail.com");
        map.add("pass","pass");

        HttpHeaders headers=new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> signInRequest=new HttpEntity<>(map,headers);

        ResponseEntity<String> signInResponse = restTemplate.exchange(LOCALHOST+AuthenticationController.SIGN_IN_DESTINATION,HttpMethod.POST,signInRequest,String.class);

        System.out.println(signInResponse);

        return signInResponse.getHeaders().get("Authorization").get(0);



    }

    public static void openConnection(String authenticationHeader){
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient transport = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(taskScheduler); // for heartbeats

        WebSocketHttpHeaders headers=new WebSocketHttpHeaders();

        headers.add("Authorization",authenticationHeader);



        StompSessionHandler myHandler = new MyStompHandler();
        String wsUrl = "ws://localhost:8080/ws";
        stompClient.connect(wsUrl, headers,  myHandler);

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        //block the thread
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class MyStompHandler extends StompSessionHandlerAdapter {
        @Override
        public Type getPayloadType(StompHeaders headers) {
            return super.getPayloadType(headers);
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println(headers);
            System.out.println(payload);
        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("session subscribed");
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
                                    Throwable exception) {
            System.out.println(exception.getMessage());
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            exception.printStackTrace();
            System.out.println("transport error.");
        }
    }
}