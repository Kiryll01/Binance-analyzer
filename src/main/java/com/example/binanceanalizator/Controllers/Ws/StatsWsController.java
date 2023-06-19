package com.example.binanceanalizator.Controllers.Ws;

import com.example.binanceanalizator.Models.Dto.TickerStatisticsDto;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import com.example.binanceanalizator.Models.Roles;
import com.example.binanceanalizator.Models.SMA;
import com.example.binanceanalizator.Services.MovingAverageService;
import com.example.binanceanalizator.Services.TickerStatisticsService;
import com.example.binanceanalizator.Services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class StatsWsController {
public static final String FETCH_TICKER_STATS ="/topic/stats.ticker_statistics";
    public static final String FETCH_SMA_STATS="/topic/user.{user_id}.stats.sma";
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

@SubscribeMapping(FETCH_SMA_STATS)
public Set<SMA> translateSMA(){
        return null;
}

@Scheduled(fixedRate = FIXED_RATE_FOR_TICKER_STATISTICS)
public void sendStatistics(){

Set<TickerStatisticsDto> statsSet=tickerStatisticsService.getLastTickerStatisticsInTimeIntervalFromMemoryDb(FIXED_RATE_FOR_TICKER_STATISTICS);

messagingTemplate.convertAndSend(FETCH_TICKER_STATS,statsSet);

//log.info("SCHEDULING : message converted and send with payload : "+statsSet);
}

//Todo: send stats to all users depending on their properties;
@Scheduled(fixedRate = FIXED_RATE_FOR_SMA)
    public void sendSMA(){
userService.getAllFromInMemoryDb().stream()
        .filter(user -> checkRole(user.getUserProperties().getRole()))
        .forEach(user ->
        user.getSymbols().forEach(s->
            sendSMAToUser(user, s)));
    }

    private void sendSMAToUser(RedisUser user, String s) {
        List<SMA> sma= calculateSMAForSymbol(user, s);
        messagingTemplate.convertAndSend(getDestination(user),sma);
    }

    private List<SMA> calculateSMAForSymbol(RedisUser user,String symbol){
       return movingAverageService.calculateSMAForAPeriod(symbol,
                user.getMovingAverageProperties().getMillisInterval(),
                user.getMovingAverageProperties().getStartTime(),
                user.getMovingAverageProperties().getEndTime());
}
    @NotNull
    private static String getDestination(RedisUser user) {
        return FETCH_SMA_STATS.replace("{user_id}", user.getSimpSessionId());
    }

    public boolean checkRole(String role){
    if(role.equals(Roles.RAW_USER.getValue()))return false;
     return true;
 }


}
