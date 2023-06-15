package com.example.binanceanalizator;

import com.example.binanceanalizator.Models.Entities.Embedded.TickerStatistics;
import com.example.binanceanalizator.Models.Entities.InMemory.TickerStatisticsRedis;
import com.example.binanceanalizator.Services.RedisOperations;
import com.example.binanceanalizator.Services.TickerStatisticsService;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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
