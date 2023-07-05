package com.example.binanceAnalyzer;

import com.example.binanceAnalyzer.Models.Entities.Embedded.TickerStatistics;
import com.example.binanceAnalyzer.Models.Entities.InMemory.MovingAverageProperties;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Models.Entities.InMemory.TickerStatisticsRedis;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserProperties;
import com.example.binanceAnalyzer.Models.Plain.Roles;
import com.example.binanceAnalyzer.Services.RedisOperations;
import com.example.binanceAnalyzer.Services.TickerStatisticsService;
import com.example.binanceAnalyzer.Services.UserService;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBTests {
    @Autowired
    EntityManager entityManager;
    @Autowired
    TickerStatisticsService tickerStatisticsService;
    @Autowired
     RedisTemplate<String, TickerStatisticsRedis> redisTemplate;
    @Autowired
    RedisOperations redisOperations;
    @Autowired
    UserService userService;

    RedisUser redisUserToSave;
//    @BeforeAll
//    public void setup(){
//        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
//        redisTemplate=new RedisTemplate<>();
//       JedisConnectionFactory jedisConnectionFactory= new JedisConnectionFactory(redisConfiguration);
//       jedisConnectionFactory.afterPropertiesSet();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//    }


    @BeforeAll
    public void setup(){
        redisUserToSave= RedisUser.builder()
                //.email("hamilka540@gmail.com")
                .name("hamilka540")
                //.pass("newPass")
                .userSymbolSubscriptions(new HashSet<>())
                .userProperties(UserProperties.builder()
                        .role(Roles.RAW_USER.getValue())
                        .movingAverageProperties(MovingAverageProperties.builder()
                                .endTime(0)
                                .longMillisInterval(10000)
                                .shortMillisInterval(50000)
                                .build())
                        .build())
                .build();
    }
    @Test
    public void saveUserInMemory(){
        userService.saveInMemory(redisUserToSave);

       RedisUser userFromDB = userService.getUserByIdFromInMemory(redisUserToSave.getId());

       log.info(redisUserToSave);

       log.info(userFromDB);

        Assertions.assertEquals(redisUserToSave,userFromDB);
    }
    @Test
    public void saveInLocalDB(){
        TickerStatistics tickerStatistics =
                TickerStatistics.builder()
                        .absoluteChange(78.056)
                        .averagePriceByCandleStick(27650.004)
                        .symbol("BTCUSDT")
                        .percentChange(0.045)
                        .build();

       TickerStatistics tickerStatisticsFromDB = tickerStatisticsService.saveInLocalDb(tickerStatistics);

       Assertions.assertEquals(tickerStatistics, tickerStatisticsFromDB);

        System.out.println(tickerStatisticsFromDB);

        System.out.println(tickerStatistics);
    }
    @Test
    public void saveInMemoryDB(){
        TickerStatisticsRedis tickerStatisticsDao=
                TickerStatisticsRedis.builder()
                        .absoluteChange(78.056)
                        .averagePriceByCandleStick(27650.004)
                        .symbol("BTCUSDT")
                        .percentChange(0.045)
                        .build();

tickerStatisticsService.saveInMemoryDb(tickerStatisticsDao);

        TickerStatisticsRedis tickerStatisticsDaoFromDB=
                (TickerStatisticsRedis) redisOperations.getLastFromInMemoryDBByKey(TickerStatisticsService.KEY);

        log.info(tickerStatisticsDaoFromDB);

        log.info(tickerStatisticsDao);

 Assertions.assertEquals(tickerStatisticsDao,tickerStatisticsDaoFromDB);

 log.info("OK");

    }

}
