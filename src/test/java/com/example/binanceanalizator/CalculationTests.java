package com.example.binanceanalizator;

import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.example.binanceanalizator.Services.TickerStatisticsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculationTests {
    @Autowired
    BinanceApiWebSocketClient binanceClient;
    @Autowired
    TickerStatisticsService tickerStatisticsService;
    String symbol="BTCUSDT";
    @Test
    public void avgSumCalculationTest() throws InterruptedException {

    }

}
