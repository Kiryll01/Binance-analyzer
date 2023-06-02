package com.example.binanceanalizator.Services;

import com.binance.api.client.domain.event.AggTradeEvent;
import com.example.binanceanalizator.Models.Dao.Embedded.AggTradeDao;
import com.example.binanceanalizator.repos.AggTradeRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AggTradeService {
final AggTradeRepo aggTradeRepo;

final RedisTemplate<String,AggTradeDao> redisTemplate;

List<String> keys=new ArrayList<>();
public AggTradeDao saveInLocalDB(AggTradeEvent aggTradeEvent) {
    AggTradeDao aggTradeDao = aggTradeDaoBuilder(aggTradeEvent);
    return aggTradeRepo.save(aggTradeDao);
}
public void saveInMemoryDb(AggTradeEvent aggTradeEvent){
    AggTradeDao aggTradeDao=aggTradeDaoBuilder(aggTradeEvent);
    keys.add(aggTradeDao.getAggregatedTradeId());
    redisTemplate.opsForSet().add(aggTradeDao.getAggregatedTradeId(),aggTradeDao);
}
public static AggTradeDao aggTradeDaoBuilder(@NotNull AggTradeEvent aggTrade){
   return AggTradeDao.builder()
            .aggregatedTradeId(String.valueOf(aggTrade.getAggregatedTradeId()))
            .price(aggTrade.getPrice())
            .symbol(aggTrade.getSymbol())
            .eventType(aggTrade.getEventType())
            .quantity(aggTrade.getQuantity())
            .eventTime(aggTrade.getEventTime())
            .build();
}

}
