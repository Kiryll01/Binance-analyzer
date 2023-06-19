package com.example.binanceanalizator.Controllers.Rest;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import com.example.binanceanalizator.Models.Factories.UserFactory;
import com.example.binanceanalizator.Models.UserPrincipal;
import com.example.binanceanalizator.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserController {
    UserService userService;
    public static final String GET_ACCOUNT_INFORMATION="/api/user/account/information";
    public static final String SET_ACCOUNT_INFORMATION="/api/user/account/edit";
    @GetMapping(GET_ACCOUNT_INFORMATION)
    public ResponseEntity<UserDto> getAccountInformation(Authentication authentication)throws UsernameNotFoundException {
       UserPrincipal userPrincipal=(UserPrincipal) userService.loadUserByUsername (authentication.getName());
    return ResponseEntity.ok()
            .body(UserFactory.makeUserDto(userPrincipal.getUser()));
    }
    //Todo: implement;
    //TODO: save user properties after disconnect
    @PutMapping(SET_ACCOUNT_INFORMATION)
    public ResponseEntity<?> setAccountInformation(@RequestBody UserDto userDto, BindingResult bindingResult,
                                                   Authentication authentication, @Header String simpSessionId){

       RedisUser redisUser= userService.getUserBySimpSessionId(simpSessionId);

       //redisUser.setRole();
return null;
      //  UserPrincipal userPrincipal= (UserPrincipal) authentication.getPrincipal();
        //userService.findUserByEmailAndPass(userDto.getEmail(), userDto.getPass());

    }
}
