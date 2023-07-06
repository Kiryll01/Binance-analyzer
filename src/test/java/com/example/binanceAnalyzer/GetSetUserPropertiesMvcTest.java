package com.example.binanceAnalyzer;

import com.example.binanceAnalyzer.Controllers.Rest.UserController;
import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import com.example.binanceAnalyzer.Models.Entities.InMemory.MovingAverageProperties;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserProperties;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserSymbolSubscription;
import com.example.binanceAnalyzer.Models.Factories.UserServiceMapper;
import com.example.binanceAnalyzer.Models.Requests.PropertiesRequestBody;
import com.example.binanceAnalyzer.Properties.BinanceProperties;
import com.example.binanceAnalyzer.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@Log4j2
//@WebMvcTest(controllers = UserController.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional
public class GetSetUserPropertiesMvcTest {
        public static final String NAME = "hamilka540";
        public static final String EMAIL = "hamilka540@gmail.com";
        public static final String PASS = "newPass";
        @Autowired
        MockMvc mockMvc;
        ObjectMapper mapper=new ObjectMapper();
        @MockBean
        UserServiceMapper userServiceMapper;
        @MockBean
        UserService userService;
        @Autowired
        WebApplicationContext context;
        HttpHeaders ssHeaders =new HttpHeaders();
        public static final String PATH="http://localhost:8080";
        User userToSave;
        @BeforeAll
        public void setup() throws Exception {

            mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .build();

            //UserPropertiesEntity.builder()

            userToSave=User.builder()
                    .email("hamilka540@gmail.com")
                    .name("hamilka540")
                    .pass("newPass")
                    .userSymbolSubscriptions(new HashSet<>())
                    //.userProperties(UserPropertiesEntity.builder().role(Roles.RAW_USER.getValue()).build())
                    .build();
        }

        @Test
        @WithMockUser(username = "kiryll",roles = "RAW")
        public void testSetUserInf() throws Exception {

         PropertiesRequestBody requestBody = PropertiesRequestBody.builder()
                 .symbolSubscriptions(Set.of(new UserSymbolSubscription("BTCUSDT"),new UserSymbolSubscription("LTCUSDT")))
                    .userProperties(UserProperties
                            .builder()
                            .role("ROLE_RAW")
                            .movingAverageProperties(MovingAverageProperties
                                    .builder()
                                    .endTime(0)
                                    .longMillisInterval(10000)
                                    .shortMillisInterval(2000)
                                    .build())
                            .build())
                    .build();

            MockHttpServletResponse badRequestResponse= mockMvc.perform(MockMvcRequestBuilders.patch(PATH+UserController.SET_PROPERTIES_INFORMATION)
                            .with(csrf())
                            .content(mapper.writeValueAsString(requestBody))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn()
                    .getResponse();

            log.info(badRequestResponse.getContentAsString());
            badRequestResponse.getHeaderNames().forEach(name-> System.out.println(badRequestResponse.getHeaderValues(name)));

            requestBody.setSymbolSubscriptions(Set.of(new UserSymbolSubscription("BTCUSDT")));

            MockHttpServletResponse okResponse= mockMvc.perform(MockMvcRequestBuilders.patch(PATH+UserController.SET_PROPERTIES_INFORMATION)
                            .with(csrf())
                            .content(mapper.writeValueAsString(requestBody))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn()
                    .getResponse();

       log.info(okResponse.getContentAsString());
       okResponse.getHeaderNames().forEach(name-> System.out.println(okResponse.getHeaderValues(name)));
        }
//        @Test
//
//        public void testGetUserInfoRequest() throws Exception{
//
//            MockHttpServletResponse getInfoResponse = mockMvc.perform(MockMvcRequestBuilders.get(PATH+ UserController.GET_ACCOUNT_INFORMATION)
//                            .headers(ssHeaders)
//                            .with(csrf())
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andReturn()
//                    .getResponse();
//
//            log.info(getInfoResponse.getContentAsString());
//        }
    @AfterAll
    @Transactional
    public void afterAll(){
        //  if(userService.deleteUserByEmailAndPass(EMAIL,PASS)!=null) userService.deleteUserByEmailAndPass("hamilka540@gmail.com","newPass");
    }
}

