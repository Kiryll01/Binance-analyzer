package com.example.binanceanalizator.repos;

import com.example.binanceanalizator.Models.Entities.Embedded.TickerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerStatisticsRepo extends JpaRepository<TickerStatistics,String> {
}
