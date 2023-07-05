package com.example.binanceAnalyzer.Services;

import com.example.binanceAnalyzer.Models.Dto.UserDto;
import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import com.example.binanceAnalyzer.Models.Entities.InMemory.IdSessionIdUser;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Models.Factories.UserServiceMapper;
import com.example.binanceAnalyzer.Models.Plain.UserPrincipal;
import com.example.binanceAnalyzer.repos.UsersRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

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
    log.info(event.getAuthentication().getName()+" is now Authenticated");
}
@EventListener
public void afterSessionEstablished(SessionConnectedEvent event){

    String sessionId= SimpAttributesContextHolder.getAttributes().getSessionId();

   String name= event.getUser().getName();

    RedisUser user = getRedisUserByName(name);

    user.setSessionId(sessionId);

replaceFromInMemory(user.getId(),user);

IdSessionIdUser idSessionIdUser=new IdSessionIdUser(sessionId, user.getId());

idHashOperations.put(USER_IDS_KEY, user.getSessionId(),idSessionIdUser);
}
@EventListener
public void afterSessionDisconnectEvent(SessionDisconnectEvent event){

    RedisUser redisUser = getUserBySessionIdFromInMemory(event.getSessionId());

    delete(findUserByName(redisUser.getName()));

    UserDto userDto=UserServiceMapper.USER_MAPPER.toUserDto(redisUser);

    save(UserServiceMapper.USER_MAPPER.toUser(userDto));

    deleteRedisUser(redisUser.getId());

    log.info(event.toString());

}

@NotNull
public RedisUser getRedisUserByName(String name) {
        RedisUser user=getAllFromInMemoryDb().stream().filter(redisUser->redisUser.getName().equals(name)).findAny().orElseThrow();
        return user;
    }
    public void delete(User user){
    usersRepo.delete(user);
}
public Integer deleteUserByEmailAndPass(String email,String pass){
   return usersRepo.deleteUserByEmailAndPass(email,pass);
}

public User save(User user){
    return usersRepo.save(user);
}

public void replaceFromInMemory(String oldId,RedisUser newUser){
    userHashOperations.delete(USERS_KEY,oldId);
    userHashOperations.put(USERS_KEY, newUser.getId(),newUser);
}
public User findUserByEmailAndPass(String email,String pass){
   return usersRepo.findUserByEmailAndPass(email,pass);
}

public User findUserByName(String name){
    return usersRepo.findUserByName(name);
}

public void saveInMemory(RedisUser user){
     userHashOperations.put(USERS_KEY,user.getId(),user);
}

public void deleteRedisUser(String id){
    userHashOperations.delete(USERS_KEY,id);
}
public List<RedisUser> getAllFromInMemoryDb(){
        return userHashOperations.values(USERS_KEY);}
    public RedisUser getUserByIdFromInMemory(String id){
   return userHashOperations.get(USERS_KEY,id);
}

public RedisUser getUserBySessionIdFromInMemory(String simpSessionId){
    String id=idHashOperations.get(USER_IDS_KEY,simpSessionId).getId();
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
