package com.example.binanceanalizator.Controllers.Rest;

import com.example.binanceanalizator.Controllers.ErrorHandlingUtils;
import com.example.binanceanalizator.Models.Dto.UserDto;
import com.example.binanceanalizator.Models.Entities.Embedded.User;
import com.example.binanceanalizator.Models.Entities.Embedded.UserPropertiesEntity;
import com.example.binanceanalizator.Models.Factories.UserFactory;
import com.example.binanceanalizator.Models.Roles;
import com.example.binanceanalizator.Models.UserPrincipal;
import com.example.binanceanalizator.Services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController {
    AuthenticationEventPublisher authenticationEventPublisher;
    AuthenticationProvider authenticationProvider;
    UserService userService;
    public static final String SIGN_UP_DESTINATION="/api/user/authentication/sign_up";
    public static final String SIGN_IN_DESTINATION="/api/user/authentication/sign_in";

    @PostMapping(SIGN_UP_DESTINATION)
    public ResponseEntity<?> signUp(@Validated @RequestBody UserDto user, BindingResult bindingResult,HttpSession httpSession)  {

        user.setUserProperties(UserPropertiesEntity.builder().role(Roles.RAW_USER.getValue()).build());

        if(bindingResult.hasErrors()) return ErrorHandlingUtils.returnBindingResultEntity(bindingResult);

if(userService.findUserByName(user.getName())!=null) return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("name already exists");

        userService.save(UserFactory.makeUser(user));

        EntityModel<UserDto> userModel=
                EntityModel.of(user,linkTo(methodOn(AuthenticationController.class)
                        .signIn(user.getEmail(), user.getPass(),httpSession)).withRel(AuthenticationController.SIGN_IN_DESTINATION));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                        .body(userModel);

    }
@PostMapping(SIGN_IN_DESTINATION)
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String pass, HttpSession httpSession){

        User user = userService.findUserByEmailAndPass(email,pass);

        if(user==null) return ResponseEntity.badRequest().body(email+" "+ pass+ " not found");

    setAuthentication(user);

    userService.saveInMemory(UserFactory.makeRedisUser(UserFactory.makeUserDto(user),httpSession.getId()));

    Authentication authentication=getAuthentication();

    UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

    UserDto userDto=UserFactory.makeUserDto(userPrincipal.getUser());

    EntityModel<UserDto> userModel=
            EntityModel.of(userDto,linkTo(methodOn(AuthenticationController.class).signIn(email, pass,httpSession)).withSelfRel());

    HttpHeaders headers = setHeaders(userPrincipal);

    authenticationEventPublisher.publishAuthenticationSuccess(authentication);

    return ResponseEntity.ok()
           .contentType(MediaType.APPLICATION_JSON)
           .headers(headers)
           .body(userModel);
}

    @NotNull
    private HttpHeaders setHeaders(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", userPrincipal.getUsername() );
        headers.add("Authorization", "Bearer "+ userPrincipal.getPassword() );
        return headers;
    }

    private Authentication getAuthentication() {
         return SecurityContextHolder.getContext().getAuthentication();
    }

    private void setAuthentication(User user) {
        Authentication authentication=authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPass()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
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

