package com.example.binanceanalizator.repos;

import com.example.binanceanalizator.Models.Entities.Embedded.UserProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPropertiesRepo extends JpaRepository<UserProperties,String> {
}
