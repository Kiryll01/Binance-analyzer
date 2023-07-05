package com.example.binanceAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BinanceAnalyzerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BinanceAnalyzerApplication.class, args);

	}

}
