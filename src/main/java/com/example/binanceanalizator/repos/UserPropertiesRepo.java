package com.example.binanceanalizator.repos;

import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPropertiesRepo extends JpaRepository<UserPropertiesEntity,String> {
}
