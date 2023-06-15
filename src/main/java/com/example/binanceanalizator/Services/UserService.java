package com.example.binanceanalizator.Services;

import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.UserPrincipal;
import com.example.binanceanalizator.repos.UsersRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {
UsersRepo usersRepo;
@EventListener
public void afterAuthenticationSuccess(AuthenticationSuccessEvent event){
    log.info(event.getAuthentication());
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
@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
}
}
