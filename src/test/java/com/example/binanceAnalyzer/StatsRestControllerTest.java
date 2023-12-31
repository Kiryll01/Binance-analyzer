package com.example.binanceAnalyzer;

import com.example.binanceAnalyzer.Controllers.Rest.StatsRestController;
import com.example.binanceAnalyzer.Models.Dto.UserDto;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserSymbolSubscription;
import com.example.binanceAnalyzer.Models.Plain.SMA;
import com.example.binanceAnalyzer.Models.Requests.SMARequestBody;
import com.example.binanceAnalyzer.Services.MovingAverageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@WebMvcTest({StatsRestController.class})
@AutoConfigureMockMvc
public class StatsRestControllerTest {
    UserDto userDto;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper=new ObjectMapper();
    @MockBean
    MovingAverageService movingAverageService;
    @Autowired
    private WebApplicationContext context;
  public static final String PATH="http://localhost:8080";
    @BeforeEach
    public void setup()  {
        userDto=UserDto
                .builder()
                .name("kiryll")
                .email("tarnokiryll@outlook.com")
                .pass("passHuiNapass")
                .userProperties(null)
                .userSymbolSubscriptions(new HashSet<>(Set.of(new UserSymbolSubscription("BTCUSDT"))))
                .build();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();


    }
    @Test
    @WithMockUser(username = "kiryll", roles = {"RAW" , "ADMIN"})
    public void testSmaRequest() throws Exception {

       String startTime="2023-04-13 00:00:00";
       String endTime="2023-05-13 00:00:00";

       SimpleDateFormat simpleDateFormat=new SimpleDateFormat(StatsRestController.format);

       Long sMillis= simpleDateFormat.parse(startTime).getTime();

       Long eMillis=simpleDateFormat.parse(endTime).getTime();

        Assertions.assertEquals( simpleDateFormat.parse(startTime).getTime(),sMillis, "time must be equal");

        Assertions.assertEquals( simpleDateFormat.parse(endTime).getTime(),eMillis, "time must be equal");

        List<SMA> smaList=new ArrayList<>();

        smaList.add(new SMA(25000,12443123));
        smaList.add(new SMA(25023,12415135));

        Mockito.when(movingAverageService.calculateSMAForAPeriod("BTCUSDT".toUpperCase(),1000*60,sMillis,eMillis)).thenReturn(smaList);

        SMARequestBody smaRequestBody=new SMARequestBody("BTCUSDT", (long) (1000*60),startTime,endTime);

        MockHttpServletRequestBuilder builder= MockMvcRequestBuilders
                .get(PATH+StatsRestController.FETCH_SMA)
                .content(mapper.writeValueAsString(smaRequestBody))
//                .param("symbol","BTCUSDT")
//                .param("interval", String.valueOf(1000*60))
//                .param("start_time",startTime)
//                .param("end_time",endTime)
                .contentType(MediaType.APPLICATION_JSON);

     MockHttpServletResponse response =mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();



       log.info(response.getHeaderNames());

       log.info(response.getStatus());

       log.info(response.getHeader("stats_name"));

        String contentAsString=response.getContentAsString();

        log.info(contentAsString);

        Assertions.assertEquals(contentAsString,mapper.writeValueAsString(smaList),"not equals");

    }
}
