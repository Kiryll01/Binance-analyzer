package com.example.binanceanalizator.configs;


import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerStatistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class BinanceRestClientConfig {
@Bean
public BinanceApiRestClient binanceApiRestClient(){
BinanceApiRestClient client=BinanceApiClientFactory.newInstance().newRestClient();

return client;
    }
}
