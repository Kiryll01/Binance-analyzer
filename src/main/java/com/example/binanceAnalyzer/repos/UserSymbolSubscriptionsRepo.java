package com.example.binanceAnalyzer.repos;

import com.example.binanceAnalyzer.Models.Entities.Embedded.UserSymbolSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSymbolSubscriptionsRepo extends JpaRepository<UserSymbolSubscriptionEntity,String> {
}
