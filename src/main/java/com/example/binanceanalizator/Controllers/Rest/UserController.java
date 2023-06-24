package com.example.binanceanalizator.Controllers.Rest;

import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.InMemory.RedisUser;
import com.example.binanceanalizator.Models.Factories.UserServiceMapper;
import com.example.binanceanalizator.Models.UserPrincipal;
import com.example.binanceanalizator.Models.ValidationUtils.UserPropertiesValidation;
import com.example.binanceanalizator.Models.ValidationUtils.ValidationException;
import com.example.binanceanalizator.Services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<UserDto> getAccountInformation(Authentication authentication)throws UsernameNotFoundException {
       UserPrincipal userPrincipal=(UserPrincipal) userService.loadUserByUsername (authentication.getName());
    return ResponseEntity.ok()
            .body(userServiceMapper.toUserDto(userPrincipal.getUser()));
    }
    //TODO: save user properties after disconnect
    @PutMapping(SET_ACCOUNT_INFORMATION)
    public ResponseEntity<?> setNewProperties(@RequestBody UserDto userDto, HttpSession httpSession) throws ValidationException {

       RedisUser redisUser=userService.getUserBySessionId(httpSession.getId());

        UserPropertiesValidation.checkMovingAverageProperties(redisUser.getUserProperties().getMovingAverageProperties());

       redisUser.getUserProperties().setMovingAverageProperties(userDto.getUserProperties().getMovingAverageProperties());

       redisUser.setUserProperties(userDto.getUserProperties());

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(redisUser);
    }
}
