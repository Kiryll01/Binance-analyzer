package com.example.binanceanalizator.repos;

import com.example.binanceanalizator.Models.Dao.Embedded.AggTradeDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AggTradeRepo extends JpaRepository<AggTradeDao, String> {
}
