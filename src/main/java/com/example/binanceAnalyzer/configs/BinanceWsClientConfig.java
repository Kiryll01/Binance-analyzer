package com.example.binanceAnalyzer.configs;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.AggTradeEvent;
import com.binance.api.client.domain.event.BookTickerEvent;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.event.DepthEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.example.binanceAnalyzer.Properties.BinanceProperties;
import com.example.binanceAnalyzer.Models.Entities.InMemory.TickerStatisticsRedis;
import com.example.binanceAnalyzer.Models.Factories.TickerStatisticsFactory;
import com.example.binanceAnalyzer.Services.BinanceStatisticsService;
import com.example.binanceAnalyzer.Services.RedisOperations;
import com.example.binanceAnalyzer.Services.TickerStatisticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BinanceWsClientConfig {
//    @Value("${app.binance.key.key}")
//    String key;
//   @Value("${app.binance.key.secret}")
//    String secret;
    final TickerStatisticsFactory tickerStatisticsFactory;
   final BinanceProperties properties;
final RedisOperations redisOperations;
   final BinanceStatisticsService binanceStatisticsService;
    public static volatile int tickerStatisticsRedisCount=0;
   final TickerStatisticsService tickerStatisticsService;
   @Bean
    public BinanceApiWebSocketClient binanceApiWebSocketClient(){
BinanceApiWebSocketClient client= BinanceApiClientFactory
        .newInstance().newWebSocketClient();
EventStreaming(client);
return client;
    }

    public void EventStreaming(BinanceApiWebSocketClient client){
   properties.getTickers().forEach(
           ticker->client.onCandlestickEvent(ticker.toLowerCase(), CandlestickInterval.ONE_MINUTE,this::fetchCandleStickEventData));
   }
    private void fetchCandleStickEventData(CandlestickEvent event){
      // log.info("raw stats : " + event);


        if(tickerStatisticsRedisCount>49) {
            TickerStatisticsRedis tickerStatisticsRedis= (TickerStatisticsRedis) redisOperations.getLastFromInMemoryDBByKey(TickerStatisticsService.KEY);
            tickerStatisticsService.deleteAll();
            tickerStatisticsService.saveInMemoryDb(tickerStatisticsRedis);
        }

        TickerStatisticsRedis tickerStatisticsRedis =tickerStatisticsService.calculateStatistics(event);
       // log.info("calculated stats : "+ tickerStatisticsRedis);

        tickerStatisticsRedisCount++;

      tickerStatisticsService.saveInMemoryDb(tickerStatisticsRedis);

     // log.info("stats from redis : " + redisOperations.getLastFromInMemoryDBByKey(TickerStatisticsService.KEY));
    }


public void fetchDepthEventData(DepthEvent event){
    System.out.println(Strings.repeat("-",100));
    System.out.println("bid price:"+event.getBids());
    System.out.println("ask price:"+event.getAsks());
    System.out.println(Strings.repeat("-",100));
}

}

