package com.example.binanceAnalyzer.Controllers.Rest;

import com.example.binanceAnalyzer.Models.Dto.UserDto;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Models.Factories.UserServiceMapper;
import com.example.binanceAnalyzer.Models.Plain.UserPrincipal;
import com.example.binanceAnalyzer.Models.Requests.PropertiesRequestBody;
import com.example.binanceAnalyzer.Services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserController {
    UserServiceMapper userServiceMapper;
    UserService userService;
    public static final String GET_ACCOUNT_INFORMATION="/api/user/account/information";
    public static final String SET_ACCOUNT_INFORMATION="/api/user/account/edit";
    @GetMapping(GET_ACCOUNT_INFORMATION)
    public ResponseEntity<UserDto> getAccountInformation()throws UsernameNotFoundException {

       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

       UserPrincipal userPrincipal=(UserPrincipal) userService.loadUserByUsername (authentication.getName());

    return ResponseEntity.ok()
            .body(userServiceMapper.toUserDto(userPrincipal.getUser()));
    }
    //TODO: save user properties after disconnect
    @PatchMapping(SET_ACCOUNT_INFORMATION)
    public ResponseEntity<RedisUser> setNewProperties(@RequestBody PropertiesRequestBody properties,Authentication authentication)  {

       String name=(String) authentication.getPrincipal();

       RedisUser redisUser=userService.getRedisUserByName(name);


       redisUser.getUserProperties().setMovingAverageProperties(properties.getUserProperties().getMovingAverageProperties());

       redisUser.setUserProperties(properties.getUserProperties());

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(redisUser);
    }


}
