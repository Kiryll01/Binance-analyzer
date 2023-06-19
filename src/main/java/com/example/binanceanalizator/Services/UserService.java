package com.example.binanceanalizator.Services;

import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import com.example.binanceanalizator.Models.UserPrincipal;
import com.example.binanceanalizator.repos.UsersRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {
UsersRepo usersRepo;
RedisTemplate<String, RedisUser> redisTemplate;
HashOperations<String,String,RedisUser> hashOperations= redisTemplate.opsForHash();
public static String KEY="binance:users";
@EventListener
public void afterAuthenticationSuccess(AuthenticationSuccessEvent event){
    log.info(event.getAuthentication().getName());
}
public Integer deleteUserByEmailAndPass(String email,String pass){
   return usersRepo.deleteUserByEmailAndPass(email,pass);
}
public User save(User user){
    return usersRepo.save(user);
}
public User findUserByEmailAndPass(String email,String pass){
   return usersRepo.findUserByEmailAndPass(email,pass);
}
public User findUserByName(String name){
    return usersRepo.findUserByName(name);
}
public void saveInMemory(RedisUser user){
    hashOperations.put(KEY,user.getId(),user);
}
public List<RedisUser> getAllFromInMemoryDb(){
        return hashOperations.values(KEY);
    }
@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
}
}
