package com.example.binanceAnalyzer.repos;

import com.example.binanceAnalyzer.Models.Entities.Embedded.UserPropertiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPropertiesRepo extends JpaRepository<UserPropertiesEntity,String> {
}
