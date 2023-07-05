package com.example.binanceAnalyzer;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.example.binanceAnalyzer.Models.Plain.EMA;
import com.example.binanceAnalyzer.Models.Plain.SMA;
import com.example.binanceAnalyzer.Services.MovingAverageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculationTests {
    @Autowired
    MovingAverageService movingAverageService;
    String symbol="BTCUSDT";
    @TestConfiguration
    static class TestConfig{
    @Bean
    public BinanceApiRestClient binanceApiRestClient() {
        BinanceApiRestClient client = BinanceApiClientFactory.newInstance().newRestClient();

        return client;
    }
    @Bean
        public MovingAverageService movingAverageService(){
        return new MovingAverageService(binanceApiRestClient());
    }
    }
    @Test
    public void smaCalculationTest() throws InterruptedException {
        LocalDateTime startTime=LocalDateTime.of(2023,3,14,15,10);
        LocalDateTime endTime=LocalDateTime.of(2023,3,15,16,10);
        Long sMillis= startTime.toEpochSecond(ZoneOffset.UTC)*1000;
        Long eMillis=endTime.toEpochSecond(ZoneOffset.UTC)*1000;

        List<SMA> smaList=movingAverageService.calculateSMAForAPeriod(symbol,1000*60,sMillis,eMillis);

        smaList.forEach(System.out::println);
    }

    @Test
    public void emaCalculationTest(){
        LocalDateTime startTime=LocalDateTime.of(2023,3,14,15,10);
        LocalDateTime endTime=LocalDateTime.of(2023,3,15,16,10);
        Long sMillis= startTime.toEpochSecond(ZoneOffset.UTC)*1000;
        Long eMillis=endTime.toEpochSecond(ZoneOffset.UTC)*1000;

       List<EMA> emaList= movingAverageService.calculateEmaForAPeriod(symbol,1000*60,sMillis,eMillis);

       emaList.forEach(System.out::println);
    }

}
