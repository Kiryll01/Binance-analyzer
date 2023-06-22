package com.example.binanceanalizator.repos;

import com.example.binanceanalizator.Models.Entities.Embedded.UserSymbolSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSymbolSubscriptionsRepo extends JpaRepository<UserSymbolSubscriptionEntity,String> {
}
