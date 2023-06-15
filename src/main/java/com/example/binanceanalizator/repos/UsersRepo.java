package com.example.binanceanalizator.repos;

import com.example.binanceanalizator.Models.Entities.Embedded.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User,String> {
User findUserByName(String name);
User findUserByEmailAndPass(String email,String pass);
}
