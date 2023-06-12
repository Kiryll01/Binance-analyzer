package com.example.binanceanalizator;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.example.binanceanalizator.Models.EMA;
import com.example.binanceanalizator.Models.SMA;
import com.example.binanceanalizator.Services.MovingAverageService;
import com.example.binanceanalizator.Services.TickerStatisticsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
