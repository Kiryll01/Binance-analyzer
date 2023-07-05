package com.example.binanceAnalyzer.repos;

import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User,String> {
User findUserByName(String name);
User findUserByEmailAndPass(String email,String pass);
Integer deleteUserByEmailAndPass(String email,String pass);
}
