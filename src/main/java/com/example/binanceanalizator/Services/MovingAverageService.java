package com.example.binanceanalizator.Services;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.example.binanceanalizator.Models.EMA;
import com.example.binanceanalizator.Models.SMA;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class MovingAverageService {
BinanceApiRestClient binanceApiRestClient;

public List<Candlestick> getCandlesticksForAPeriod(String symbol,long millisInterval, Long startTime,Long endTime){
   return binanceApiRestClient.getCandlestickBars(symbol,
           fromMillisToCandlestickInterval(millisInterval), null,startTime, endTime);
}
public List<SMA> calculateSMAForAPeriod(String symbol,long millisInterval, Long startTime,Long endTime) {

   symbol=symbol.toUpperCase();

   List<Candlestick> candlesticks = getCandlesticksForAPeriod(symbol,millisInterval,startTime,endTime);

   List<Double> closePrices = new ArrayList<>(500);

   List<Long> closeTimes = new ArrayList<>(500);

   candlesticks.forEach(candlestick -> {
      closePrices.add(Double.parseDouble(candlestick.getClose()));
      closeTimes.add(candlestick.getCloseTime());
   });

   int smaPeriod;

//   int smaLongPeriod;
//
//   if (closePrices.size() <= 1) return new ArrayList<>();
//
//   if (closePrices.size() < 10) smaPeriod = 2;
//
//   if (closePrices.size() < 60) smaPeriod = 3;
//
//   else smaPeriod = closePrices.size() / 20;
//
//   smaLongPeriod = smaPeriod * 3;

   smaPeriod =closePrices.size()/20;

   List<Double> smaAverages= calculateSimpleAverages(closePrices,smaPeriod);

   //List<Double> smaLongAverages = calculateSimpleAverages(closePrices,smaLongPeriod);

   List<SMA> smaList=new ArrayList<>();

   for (int i = 0; i < smaPeriod; i++) {
      smaList.add(new SMA(-1,closeTimes.remove(0)));
   }
   for (int i = 0; i <smaAverages.size() ; i++) {
      smaList.add(new SMA(smaAverages.get(i), closeTimes.get(i)));
   }
   return smaList;
}

public List<EMA> calculateEmaForAPeriod(String symbol,long millisInterval, Long startTime,Long endTime){

   symbol=symbol.toUpperCase();

   List<Candlestick> candlesticks = getCandlesticksForAPeriod(symbol,millisInterval,startTime,endTime);

   List<Double> closePrices = new ArrayList<>(500);

   List<Long> closeTimes = new ArrayList<>(500);

   candlesticks.forEach(candlestick -> {
      closePrices.add(Double.parseDouble(candlestick.getClose()));
      closeTimes.add(candlestick.getCloseTime());
   });

   int smaPeriod=closePrices.size()/20;

   if(smaPeriod==0) return new ArrayList<>();

   double sma=closePrices.subList(0,smaPeriod)
           .stream()
           .mapToDouble(o->o)
           .sum() /smaPeriod;

   List<EMA> emaList=new ArrayList<>();

   double multiplier=2/(closePrices.size()+1);

   for (int i = 0; i <smaPeriod ; i++) {
      closePrices.remove(0);
      emaList.add(new EMA(-1, closeTimes.remove(0)));
   }

   closePrices.remove(0);

   emaList.add(new EMA(sma,closeTimes.remove(0)));

   closePrices.forEach(closePrice->{
      double lastEma=emaList.get(emaList.size()-1).getValue();
      double newEma=(closePrice -lastEma) * multiplier + lastEma;
      emaList.add(new EMA(newEma,closeTimes.remove(0)));
   });

   return emaList;
}

private List<Double> calculateSimpleAverages(List<Double> closePrices, double smaPeriod){
   List<Double> smaAverages=new ArrayList<>(50);

   for (int i = 0; i < closePrices.size()-smaPeriod ; i++) {
      double bidSum=0;
      for (int j = 0; j < smaPeriod  ; j++) {
         bidSum+=closePrices.get(i+j);
      }
      smaAverages.add(bidSum/smaPeriod);
   }
   return smaAverages;
}

public static CandlestickInterval fromMillisToCandlestickInterval(long millis){
   long seconds=millis/1000;
   if(seconds<=60) return CandlestickInterval.ONE_MINUTE;
   if(seconds<=180) return CandlestickInterval.THREE_MINUTES;
   if(seconds<=60*5) return CandlestickInterval.FIVE_MINUTES;
   if(seconds<=60*15) return CandlestickInterval.FIFTEEN_MINUTES;
   if(seconds<=60*30) return CandlestickInterval.HALF_HOURLY;
   if(seconds<=60*60) return CandlestickInterval.HOURLY;
   if(seconds<=60*60*2) return CandlestickInterval.TWO_HOURLY;
   if(seconds<=60*60*4) return CandlestickInterval.FOUR_HOURLY;
   if(seconds<=60*60*6) return CandlestickInterval.SIX_HOURLY;
   if(seconds<=60*60*8) return CandlestickInterval.EIGHT_HOURLY;
   if(seconds<=60*60*12) return CandlestickInterval.TWELVE_HOURLY;
   if(seconds<=60*60*24) return CandlestickInterval.DAILY;
   if(seconds<=60*60*24*3) return CandlestickInterval.THREE_DAILY;
   if(seconds<=60*60*24*7) return CandlestickInterval.WEEKLY;
   if(seconds<=60*60*24*31) return CandlestickInterval.MONTHLY;
   else if (seconds>60*60*24*31) return CandlestickInterval.MONTHLY;
   else return CandlestickInterval.ONE_MINUTE;

}
}