package com.example.binanceAnalyzer.Models.Factories;

import com.example.binanceAnalyzer.Models.Entities.InMemory.TickerStatisticsRedis;
import com.example.binanceAnalyzer.Models.Dto.TickerStatisticsDto;
import org.springframework.stereotype.Component;

@Component
public class TickerStatisticsFactory {
public TickerStatisticsRedis fromDtoToRedisObject(TickerStatisticsDto tickerStatisticsDto){
    return TickerStatisticsRedis.builder()
            .absoluteChange(tickerStatisticsDto.getAbsoluteChange())
            .symbol(tickerStatisticsDto.getSymbol())
            .percentChange(tickerStatisticsDto.getPercentChange())
            .averagePriceByCandleStick(tickerStatisticsDto.getAveragePriceByCandleStick())
            .build();
}
public TickerStatisticsDto fromRedisToDtoObject(TickerStatisticsRedis tickerStatisticsRedis){
    return TickerStatisticsDto.builder()
            .absoluteChange(tickerStatisticsRedis.getAbsoluteChange())
            .symbol(tickerStatisticsRedis.getSymbol())
            .percentChange(tickerStatisticsRedis.getPercentChange())
            .averagePriceByCandleStick(tickerStatisticsRedis.getAveragePriceByCandleStick())
            .build();
}
}
