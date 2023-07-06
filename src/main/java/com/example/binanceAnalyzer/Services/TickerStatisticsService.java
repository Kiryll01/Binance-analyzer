package com.example.binanceAnalyzer.Services;

import com.binance.api.client.domain.event.CandlestickEvent;
import com.example.binanceAnalyzer.Models.Entities.Embedded.TickerStatistics;
import com.example.binanceAnalyzer.Models.Entities.InMemory.TickerStatisticsRedis;
import com.example.binanceAnalyzer.Models.Dto.TickerStatisticsDto;
import com.example.binanceAnalyzer.Models.Factories.TickerStatisticsFactory;
import com.example.binanceAnalyzer.repos.TickerStatisticsRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class TickerStatisticsService {
TickerStatisticsRepo repository;
RedisTemplate<String, TickerStatisticsRedis> redisTemplate;
public static String KEY ="binance:ticker_statistics";
RedisOperations redisOperations;
TickerStatisticsFactory tickerStatisticsFactory;

public Set<TickerStatisticsDto> getLastTickerStatisticsInTimeIntervalFromMemoryDb(long interval){
    long now=Instant.now().getEpochSecond();
    return redisTemplate.opsForHash().values(KEY)
            .stream()
            .map(ticker->TickerStatisticsRedis.class.cast(ticker))
            .filter(ticker->(now-ticker.getCreatedAt())<interval)
            .map(tickerStatisticsRedis -> tickerStatisticsFactory.fromRedisToDtoObject(tickerStatisticsRedis))
            .collect(Collectors.toSet());
}

public TickerStatisticsRedis calculateStatistics(CandlestickEvent candlestickEvent){

    double avgPrice=(Double.parseDouble(candlestickEvent.getClose())+
            Double.parseDouble(candlestickEvent.getHigh())+
            Double.parseDouble(candlestickEvent.getLow())+
            Double.parseDouble( candlestickEvent.getOpen()))/4;

TickerStatisticsRedis lastTickerStatisticsRedis =
        (TickerStatisticsRedis) redisOperations.getLastFromInMemoryDBByKey(KEY);

double lastAvgPrice= lastTickerStatisticsRedis.getAveragePriceByCandleStick();

   double absoluteChange=avgPrice-lastAvgPrice;

   double percentChange=absoluteChange/lastAvgPrice;

return TickerStatisticsRedis.builder()
          .symbol(candlestickEvent.getSymbol())
          .absoluteChange(absoluteChange)
          .averagePriceByCandleStick(avgPrice)
          .percentChange(percentChange)
          .build();

}
public void saveInMemoryDb(TickerStatisticsRedis tickerStatisticsRedis){

    redisTemplate.opsForHash().put(KEY,tickerStatisticsRedis.getId(),tickerStatisticsRedis);
}
public void deleteAll(){
   Set<Object> keys= redisTemplate.opsForHash().keys(KEY);
    redisTemplate.opsForHash().delete(KEY,keys);
}

public TickerStatistics saveInLocalDb(TickerStatistics tickerStatistics){

    return repository.save(tickerStatistics);
}

}
