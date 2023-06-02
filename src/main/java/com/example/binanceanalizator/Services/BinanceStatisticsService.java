package com.example.binanceanalizator.Services;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerStatistics;
import com.example.binanceanalizator.Properties.BinanceProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BinanceStatisticsService {
BinanceApiRestClient  restClient;
BinanceProperties properties;

public Set<TickerStatistics> getStatistics(){

 Set<TickerStatistics> stats=new HashSet<>();
 for (String ticker : properties.getTickers()) {
  stats.add(restClient.get24HrPriceStatistics(ticker));
 }
return stats;
}
}
