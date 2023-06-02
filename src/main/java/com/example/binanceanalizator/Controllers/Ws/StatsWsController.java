package com.example.binanceanalizator.Controllers.Ws;

import com.binance.api.client.domain.market.TickerStatistics;
import com.example.binanceanalizator.Models.Dto.TickerStatisticsDto;
import com.example.binanceanalizator.Services.BinanceStatisticsService;
import com.example.binanceanalizator.Services.RedisOperations;
import com.example.binanceanalizator.Services.TickerStatisticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class StatsWsController {
public static final String FETCH_BINANCE_STATS="/topic/stats";
TickerStatisticsService tickerStatisticsService;
SimpMessagingTemplate messagingTemplate;

public final static long FIXED_RATE=500;

    @SubscribeMapping(FETCH_BINANCE_STATS)
    public Set<TickerStatisticsDto> translateStats(){
        return null;
}
@Scheduled(fixedRate = FIXED_RATE)
public void sendStatistics(){

Set<TickerStatisticsDto> statsSet=tickerStatisticsService.getLastTickerStatisticsInTimeIntervalFromMemoryDb(FIXED_RATE);

messagingTemplate.convertAndSend(FETCH_BINANCE_STATS,statsSet);

log.info("SCHEDULING : message converted and send with payload : "+statsSet);
}
}