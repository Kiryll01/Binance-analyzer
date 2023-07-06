package com.example.binanceAnalyzer.Controllers.Ws;

import com.example.binanceAnalyzer.Models.Dto.TickerStatisticsDto;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Models.Plain.EMA;
import com.example.binanceAnalyzer.Models.Plain.Roles;
import com.example.binanceAnalyzer.Models.Plain.SMA;
import com.example.binanceAnalyzer.Services.MovingAverageService;
import com.example.binanceAnalyzer.Services.TickerStatisticsService;
import com.example.binanceAnalyzer.Services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@RestController
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class StatsWsController {
public static final String FETCH_TICKER_STATS ="/topic/stats.ticker_statistics";
    public static final String FETCH_MA_STATS ="/user/{username}/queue/stats.sma_statistics";
TickerStatisticsService tickerStatisticsService;
MovingAverageService movingAverageService;
SimpMessagingTemplate messagingTemplate;
UserService userService;

public final static long FIXED_RATE_FOR_TICKER_STATISTICS=500;

public final static long FIXED_RATE_FOR_SMA=1000;

    @SubscribeMapping(FETCH_TICKER_STATS)
    public Set<TickerStatisticsDto> translateStats(){
        return null;
}

@SubscribeMapping(FETCH_MA_STATS)
public Set<SMA> translateMA(){
        return null;
}

@Scheduled(fixedRate = FIXED_RATE_FOR_TICKER_STATISTICS)
public void sendStatistics(){

Set<TickerStatisticsDto> statsSet=tickerStatisticsService.getLastTickerStatisticsInTimeIntervalFromMemoryDb(FIXED_RATE_FOR_TICKER_STATISTICS);

messagingTemplate.convertAndSend(FETCH_TICKER_STATS,statsSet);

//log.info("SCHEDULING : message converted and send with payload : "+statsSet);
}

//TODO : send Advices to buy or sell for each user
@Scheduled(fixedRate = FIXED_RATE_FOR_SMA)
    public void sendMovingAverages(){
userService.getAllFromInMemoryDb().stream()
        .filter(user -> checkRole(user.getUserProperties().getRole()))
        .filter(user->checkSessionId(user.getSessionId()))
        .forEach(user ->
        user.getUserSymbolSubscriptions().forEach(uss->
                calculateAndSendMovingAveragesToUser(user, uss.getSymbolName())
        ));
    }
    private void calculateAndSendMovingAveragesToUser(RedisUser user, String symbol) {

      long shortMillisInterval = user.getUserProperties().getMovingAverageProperties().getShortMillisInterval();

      long longMillisInterval=user.getUserProperties().getMovingAverageProperties().getLongMillisInterval();

      long endTime=Instant.now().getEpochSecond();

      sendSma(user, symbol, shortMillisInterval, longMillisInterval, endTime);

      sendEma(user, symbol, shortMillisInterval, longMillisInterval, endTime);

      log.info("Moving averages data send to user : " + user);

    }

    private void sendSma(RedisUser user, String symbol, long shortMillisInterval, long longMillisInterval, long endTime) {
        List<SMA> shortSma= movingAverageService.calculateSMAForAPeriod(symbol,
        user.getUserProperties().getMovingAverageProperties().getShortMillisInterval(),
                endTime - shortMillisInterval *50,
                endTime);

        List<SMA> longSma=movingAverageService.calculateSMAForAPeriod(symbol,
                user.getUserProperties().getMovingAverageProperties().getLongMillisInterval(),
                endTime - longMillisInterval *50,
                endTime);


        messagingTemplate.convertAndSendToUser(user.getSessionId(), FETCH_MA_STATS,shortSma);

        messagingTemplate.convertAndSendToUser(user.getSessionId(), FETCH_MA_STATS,longSma);
    }

    private void sendEma(RedisUser user, String symbol, long shortMillisInterval, long longMillisInterval, long endTime) {
        List<EMA> shortEma= movingAverageService.calculateEmaForAPeriod(symbol,
                shortMillisInterval,
                endTime - shortMillisInterval *50,
                endTime);

        List<EMA> longEma=movingAverageService.calculateEmaForAPeriod(symbol,
                longMillisInterval,
                endTime - longMillisInterval *50,
                endTime);

        messagingTemplate.convertAndSendToUser(user.getSessionId(), FETCH_MA_STATS,shortEma);

        messagingTemplate.convertAndSendToUser(user.getSessionId(), FETCH_MA_STATS,longEma);
    }
    public boolean checkRole(String role){
        return !role.equals(Roles.RAW_USER.getValue());
    }
    public boolean checkSessionId(String sessionId){
        return sessionId != null && sessionId.length() > 0;
    }

    @NotNull
    private static String getDestination(String sessionId) {
        return FETCH_MA_STATS.replace("{username}", sessionId);
    }



}
