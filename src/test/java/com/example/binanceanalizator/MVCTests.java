package com.example.binanceanalizator;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.example.binanceanalizator.Controllers.Rest.StatsRestController;
import com.example.binanceanalizator.Models.SMA;
import com.example.binanceanalizator.Models.SMARequestBody;
import com.example.binanceanalizator.Services.MovingAverageService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.time.ZoneOffset.*;
@Log4j2
@WebMvcTest(StatsRestController.class)
@AutoConfigureMockMvc
public class MVCTests {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper mapper=new ObjectMapper();
    @MockBean
    MovingAverageService movingAverageService;

  public static final String PATH="http://localhost:8080";
    @Test
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
