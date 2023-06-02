package com.example.binanceanalizator.configs;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.example.binanceanalizator.Models.Dao.InMemory.TickerStatisticsRedis;
import com.example.binanceanalizator.Properties.BinanceProperties;
import com.example.binanceanalizator.Services.RedisOperations;
import com.example.binanceanalizator.Services.TickerStatisticsService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Marker;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ApplicationLifecycle {
    RedisTemplate redisTemplate;
    BinanceApiRestClient binanceApiRestClient;
    BinanceProperties properties;
    TickerStatisticsService  tickerStatisticsService;
    RedisOperations redisOperations;
    //TODO: be careful, because all data is cleaned up at the start of application
    @PostConstruct
    public void CleanAndFillRedisData(){
redisTemplate.getConnectionFactory().getConnection().flushAll();

    log.warn("redis is cleaned");

    System.out.println("new Redis data : ");

    properties.getTickers().stream()
            .map( ticker->makeMap(ticker))
            .forEach( map -> {
                for (String symbol: map.keySet()) {
                        tickerStatisticsService.saveInMemoryDb(calculateCandleStick(symbol,map.get(symbol)));
                }
            });

    log.info(redisOperations.getAllFromInMemoryDbByKey(TickerStatisticsService.KEY));
    }
    private TickerStatisticsRedis calculateCandleStick(String symbol,Candlestick candlestick){
        double avgPrice=(Double.parseDouble(candlestick.getClose())+
                Double.parseDouble(candlestick.getHigh())+
                Double.parseDouble(candlestick.getLow())+
                Double.parseDouble( candlestick.getOpen()))/4;

    return TickerStatisticsRedis.builder()
            .symbol(symbol)
            .percentChange(0)
            .absoluteChange(0)
            .averagePriceByCandleStick(avgPrice)
            .build();
    }
    private Map<String ,Candlestick> makeMap(String symbol){
       Map<String,Candlestick> map=new HashMap<>();
     map.put(symbol, binanceApiRestClient.getCandlestickBars(symbol, CandlestickInterval.ONE_MINUTE).get(499));
     log.info(map);
      return map;
    }
}

