package com.example.binanceAnalyzer.repos;

import com.example.binanceAnalyzer.Models.Entities.Embedded.TickerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerStatisticsRepo extends JpaRepository<TickerStatistics,String> {
}
