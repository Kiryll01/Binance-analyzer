package com.example.binanceAnalyzer.configs;


import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceRestClientConfig {
@Bean
public BinanceApiRestClient binanceApiRestClient(){
BinanceApiRestClient client=BinanceApiClientFactory.newInstance().newRestClient();

return client;
    }
}
