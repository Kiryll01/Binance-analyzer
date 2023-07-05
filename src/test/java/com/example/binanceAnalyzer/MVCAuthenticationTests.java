package com.example.binanceAnalyzer;

import com.example.binanceAnalyzer.Controllers.Rest.AuthenticationController;
import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import com.example.binanceAnalyzer.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceAnalyzer.Models.Factories.UserServiceMapper;
import com.example.binanceAnalyzer.Models.Plain.Roles;
import com.example.binanceAnalyzer.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class MVCAuthenticationTests {
    @Autowired
    UserServiceMapper userServiceMapper;
    public static final String NAME = "hamilka540";
    public static final String EMAIL = "hamilka540@gmail.com";
    public static final String PASS = "newPass";
    WebTestClient testClient;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper=new ObjectMapper();
    @Autowired
    UserService userService;
    @Autowired
     WebApplicationContext context;
    HttpHeaders ssHeaders =new HttpHeaders();
    public static final String PATH="http://localhost:8080";
    User userToSave;
    @BeforeAll
    public void setup()  {

        testClient=WebTestClient
                .bindToServer()
                .baseUrl(PATH)
                .build();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        userToSave=User.builder()
                .email("hamilka540@gmail.com")
                .name("hamilka540")
                .pass("newPass")
                .userSymbolSubscriptions(new HashSet<>())
                .userProperties(UserPropertiesEntity.builder().role(Roles.RAW_USER.getValue()).build())
                .build();



    }
    @AfterAll
    @Transactional
    public void afterAll(){
     //  if(userService.deleteUserByEmailAndPass(EMAIL,PASS)!=null) userService.deleteUserByEmailAndPass("hamilka540@gmail.com","newPass");
    }
    @Test
    ///@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})

    public  void testSignUp() throws Exception {

        if(userService.findUserByEmailAndPass(EMAIL, PASS)!=null) userService.deleteUserByEmailAndPass(EMAIL,PASS);

        User userToSave=User.builder()
                .email(EMAIL)
                .name(NAME)
                .pass(PASS)
                .build();

        //     Mockito.when(userService.save(userToSave)).thenReturn(userToSave);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(PATH+ AuthenticationController.SIGN_UP_DESTINATION)
                        .with(csrf())
                        .content(mapper.writeValueAsString(userServiceMapper.toUserDto(userToSave)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        log.info(response.getContentAsString());

        userService.delete(userToSave);
    }

    @Test
   // @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testSignIn() throws Exception {

       // Mockito.when(userService.findUserByEmailAndPass(email,pass)).thenReturn(userToSave);

        userService.save(userToSave);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(PATH+AuthenticationController.SIGN_IN_DESTINATION)
                        .with(csrf())
                        .param("email",EMAIL)
                        .param("pass",PASS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

       log.info( userService.getAllFromInMemoryDb());

        log.info(response.getContentAsString());

        response.getHeaderNames().forEach(name-> System.out.println(name+" "+response.getHeaderValues(name)));


        ssHeaders.add("Authorization",response.getHeader("Authorization"));
        ssHeaders.add("User-Agent",response.getHeader("User-Agent"));
       // AuthenticationManager

//Flux<String> flux=testClient.get()
//        .uri(UserController.GET_ACCOUNT_INFORMATION)
//                .header("Authorization",response.getHeader("Authorization"))
//                .header("User-Agent",response.getHeader("User-Agent"))
//                        .exchange()
//                                .returnResult(String.class)
//                                        .getResponseBody();
//flux.log();
//mockMvc.perform(MockMvcRequestBuilders.get(PATH+UserController.GET_ACCOUNT_INFORMATION))
        userService.delete(userToSave);

    }

}
