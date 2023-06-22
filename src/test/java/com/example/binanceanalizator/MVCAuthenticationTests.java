package com.example.binanceanalizator;

import com.example.binanceanalizator.Controllers.Rest.AuthenticationController;
import com.example.binanceanalizator.Controllers.Rest.StatsRestController;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Factories.UserFactory;
import com.example.binanceanalizator.Services.MovingAverageService;
import com.example.binanceanalizator.Services.UserService;
import com.example.binanceanalizator.configs.WebSecurityConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Transient;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

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
    public static final String NAME = "hamilka540";
    public static final String EMAIL = "hamilka540@gmail.com";
    public static final String PASS = "newPass";
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper=new ObjectMapper();
    @Autowired
    UserService userService;
    @Autowired
     WebApplicationContext context;
    public static final String PATH="http://localhost:8080";
    @BeforeAll
    public void setup()  {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        //UserPropertiesEntity.builder()

        User userToSave=User.builder()
                .email("hamilka540@gmail.com")
                .name("hamilka540")
                .pass("newPass")
                .userSymbolSubscriptions(new HashSet<>())
                .userProperties(new UserPropertiesEntity())
                .build();

        userService.save(userToSave);

    }
    @AfterAll
    @Transactional
    public void afterAll(){
       if(userService.deleteUserByEmailAndPass(EMAIL,PASS)!=null) userService.deleteUserByEmailAndPass("hamilka540@gmail.com","newPass");
    }
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testSignIn() throws Exception {

       // Mockito.when(userService.findUserByEmailAndPass(email,pass)).thenReturn(userToSave);

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

    }
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})

    public void testSignUp() throws Exception {

       if(userService.findUserByEmailAndPass(EMAIL, PASS)!=null) userService.deleteUserByEmailAndPass(EMAIL,PASS);

        User userToSave=User.builder()
                .email(EMAIL)
                .name(NAME)
                .pass(PASS)
                .build();

   //     Mockito.when(userService.save(userToSave)).thenReturn(userToSave);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(PATH+ AuthenticationController.SIGN_UP_DESTINATION).with(csrf())
                        .content(mapper.writeValueAsString(UserFactory.makeUserDto(userToSave)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        log.info(response.getContentAsString());
    }
}
