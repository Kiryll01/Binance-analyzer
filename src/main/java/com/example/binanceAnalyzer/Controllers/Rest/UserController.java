package com.example.binanceAnalyzer.Controllers.Rest;

import com.example.binanceAnalyzer.Models.Dto.UserDto;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserSymbolSubscription;
import com.example.binanceAnalyzer.Models.Factories.UserServiceMapper;
import com.example.binanceAnalyzer.Models.Plain.UserPrincipal;
import com.example.binanceAnalyzer.Models.Requests.PropertiesRequestBody;
import com.example.binanceAnalyzer.Properties.BinanceProperties;
import com.example.binanceAnalyzer.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserController {
    ObjectMapper mapper;
    BinanceProperties binanceProperties;
    UserServiceMapper userServiceMapper;
    UserService userService;
    public static final String GET_ACCOUNT_INFORMATION="/api/user/account/information";
    public static final String SET_PROPERTIES_INFORMATION ="/api/user/account/properties/edit";
    public static final String SET_ACCOUNT_INFORMATION ="/api/user/account/edit";
    @GetMapping(GET_ACCOUNT_INFORMATION)
    public ResponseEntity<UserDto> getAccountInformation()throws UsernameNotFoundException {

       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

       UserPrincipal userPrincipal=(UserPrincipal) userService.loadUserByUsername (authentication.getName());

    return ResponseEntity.ok()
            .body(userServiceMapper.toUserDto(userPrincipal.getUser()));
    }
    @PatchMapping(SET_PROPERTIES_INFORMATION)
    public ResponseEntity<RedisUser> setNewProperties(@RequestBody PropertiesRequestBody properties,Authentication authentication) throws JsonProcessingException {

       checkProperties(properties.getSymbolSubscriptions());

       String name=(String) authentication.getPrincipal();

       RedisUser redisUser=userService.getRedisUserByName(name);

       redisUser.getUserProperties().setMovingAverageProperties(properties.getUserProperties().getMovingAverageProperties());

       redisUser.setUserProperties(properties.getUserProperties());

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(redisUser);
    }

public void checkProperties(Set<UserSymbolSubscription> symbols) throws JsonProcessingException {

Set<UserSymbolSubscription> brokenSymbols=new HashSet<>();

    symbols.stream().filter(userSymbolSubscription -> !binanceProperties.getTickers().contains(userSymbolSubscription.getSymbolName()))
            .forEach(symbol->brokenSymbols.add(symbol));

   if(!brokenSymbols.isEmpty())throw new IllegalArgumentException(mapper.writeValueAsString(brokenSymbols));
    }

@ExceptionHandler(value = IllegalArgumentException.class)
public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
      return ResponseEntity.badRequest()
              .body(e.getMessage()+" invalid ticker names");
}
//TODO : allow user to change pass, email and name, but with additional authentication
//@PatchMapping(SET_ACCOUNT_INFORMATION)
//public ResponseEntity<RedisUser> setNewAccountInformation(@RequestBody){
//
//}
}
