package com.example.binanceanalizator.Services;

import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.InMemory.IdSessionIdUser;
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
RedisTemplate<String, RedisUser> userRedisTemplate;
HashOperations<String,String,RedisUser> userHashOperations;
HashOperations<String,String, IdSessionIdUser> idHashOperations;
public static final String USERS_KEY ="binance:users";
public static final String USER_IDS_KEY ="binance:users:ids";
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
     userHashOperations.put(USERS_KEY,user.getId(),user);
     IdSessionIdUser idSessionIdUser=new IdSessionIdUser(user.getSessionId(), user.getId());
     idHashOperations.put(USER_IDS_KEY, user.getId(),idSessionIdUser);
}

    public List<RedisUser> getAllFromInMemoryDb(){
        return userHashOperations.values(USERS_KEY);
    }
    public RedisUser getUserByIdFromInMemory(String id){
   return userHashOperations.get(USERS_KEY,id);
}

    public RedisUser getUserBySessionId(String sessionId){
    String id=idHashOperations.get(USER_IDS_KEY,sessionId).getId();
    return getUserByIdFromInMemory(id);
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
