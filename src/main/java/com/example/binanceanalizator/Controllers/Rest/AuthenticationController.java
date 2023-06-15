package com.example.binanceanalizator.Controllers.Rest;

import com.example.binanceanalizator.Controllers.ErrorHandlingUtils;
import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Factories.UserFactory;
import com.example.binanceanalizator.Services.UserService;
import com.example.binanceanalizator.repos.UsersRepo;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController {
    AuthenticationProvider authenticationProvider;
    UserService userService;
    PasswordEncoder encoder;
    public static final String SIGN_UP_DESTINATION="/api/user/authentication/sign_up";
    public static final String SIGN_IN_DESTINATION="/api/user/authentication/sign_in";

    @PostMapping(SIGN_UP_DESTINATION)
    public ResponseEntity<?> signUp(@Validated @RequestBody UserDto user, BindingResult bindingResult)  {
//        if(bindingResult.hasErrors())
//            throw new ValidationException(bindingResult.getAllErrors(),user.getClass(),"wrong data");

       //log.info(user);

        if(bindingResult.hasErrors()) return ErrorHandlingUtils.returnBindingResultEntity(bindingResult);

if(userService.findUserByName(user.getName())!=null) return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("name already exists");

        userService.save(UserFactory.makeUser(user));

        EntityModel<UserDto> userModel=
                EntityModel.of(user,linkTo(methodOn(AuthenticationController.class)
                        .signIn(user.getEmail(), user.getPass())).withRel(AuthenticationController.SIGN_IN_DESTINATION));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                        .body(userModel);

    }
@PostMapping(SIGN_IN_DESTINATION)
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String pass ){

        User user = userService.findUserByEmailAndPass(email,pass);

        if(user==null) return ResponseEntity.notFound().build();

   UserDto userDto=UserFactory.makeUserDto(user);

    Authentication authentication=authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),encoder.encode(user.getPass())));

     SecurityContextHolder.getContext().setAuthentication(authentication);

    EntityModel<UserDto> userModel=
            EntityModel.of(userDto,linkTo(methodOn(AuthenticationController.class).signIn(email, pass)).withSelfRel());
                 //   linkTo(methodOn(UserController.class).getAccountInformation(authentication)).withRel(UserController.GET_ACCOUNT_INFORMATION));


   return ResponseEntity.ok()
           .contentType(MediaType.APPLICATION_JSON)
           .body(userModel);
}


//@ExceptionHandler(ValidationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Map<String,String>> handleValidationExceptions(ValidationException validationException){
//
//        Map<String,String> errors=new HashMap<>();
//
//        validationException.getErrors().forEach(objectError ->errors.put(objectError.getObjectName(),objectError.getCode()));
//
//   HttpHeaders headers= new HttpHeaders();
//
//   headers.add("class_name",validationException.getClazz().getName());
//
//        return ResponseEntity
//                .badRequest()
//                .contentType(MediaType.APPLICATION_JSON)
//                .headers(headers)
//                .body(errors);
//}
}

