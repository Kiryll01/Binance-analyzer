package com.example.binanceAnalyzer.Controllers.Rest;

import com.example.binanceAnalyzer.Controllers.ErrorHandlingUtils;
import com.example.binanceAnalyzer.Models.Dto.UserDto;
import com.example.binanceAnalyzer.Models.Entities.Embedded.User;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Models.Entities.InMemory.UserProperties;
import com.example.binanceAnalyzer.Models.Factories.UserServiceMapper;
import com.example.binanceAnalyzer.Models.Plain.Roles;
import com.example.binanceAnalyzer.Models.Plain.UserPrincipal;
import com.example.binanceAnalyzer.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    UserServiceMapper userServiceMapper;
    AuthenticationProvider authenticationProvider;
    UserService userService;
    public static final String SIGN_UP_DESTINATION="/api/user/authentication/sign_up";
    public static final String SIGN_IN_DESTINATION="/api/user/authentication/sign_in";

    @PostMapping(SIGN_UP_DESTINATION)
    public ResponseEntity<?> signUp(HttpServletRequest request,@Validated @RequestBody UserDto userDto, BindingResult bindingResult,HttpSession httpSession)  {

        userDto.setUserProperties(UserProperties.builder().role(Roles.RAW_USER.getValue()).build());

        if(bindingResult.hasErrors()) return ErrorHandlingUtils.returnBindingResultEntity(bindingResult);

if(userService.findUserByName(userDto.getName())!=null) return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("name already exists");

        userService.save(userServiceMapper.toUser(userDto));

        EntityModel<UserDto> userModel=
                EntityModel.of(userDto,linkTo(methodOn(AuthenticationController.class)
                        .signIn(request,userDto.getEmail(), userDto.getPass())).withRel(AuthenticationController.SIGN_IN_DESTINATION));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                        .body(userModel);

    }
@PostMapping(SIGN_IN_DESTINATION)
    public ResponseEntity<?> signIn(HttpServletRequest request, @RequestParam String email, @RequestParam String pass){

        User user = userService.findUserByEmailAndPass(email,pass);

        if(user==null) return ResponseEntity.badRequest().body(email+" "+ pass+ " not found");

      UsernamePasswordAuthenticationToken token= setAuthentication(user,request);

   RedisUser redisUser= userServiceMapper.toRedisUser(userServiceMapper.toUserDto(user));

   userService.saveInMemory(redisUser);

    Authentication authentication=getAuthentication();

    UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

    UserDto userDto= userServiceMapper.toUserDto(userPrincipal.getUser());

    EntityModel<UserDto> userModel= EntityModel.of(userDto);
           // EntityModel.of(userDto,linkTo(methodOn(AuthenticationController.class).signIn(request,email, pass,httpSession)).withSelfRel());

    HttpHeaders headers = setHeaders(userPrincipal.getUsername(),token);

    return ResponseEntity.ok()
           .contentType(MediaType.APPLICATION_JSON)
           .headers(headers)
           .body(userModel);
}

    @NotNull
    private HttpHeaders setHeaders(String userName,UsernamePasswordAuthenticationToken token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", userName);
        headers.setBasicAuth((String) token.getPrincipal(), (String) token.getCredentials());
        //headers.add("Authorization", "Bearer "+passwordEncoder.encode(token));
        return headers;
    }

    private Authentication getAuthentication() {
         return SecurityContextHolder.getContext().getAuthentication();
    }

    private UsernamePasswordAuthenticationToken setAuthentication(User user,HttpServletRequest request) {

        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(user.getName(), user.getPass(),
                Collections.singleton(new SimpleGrantedAuthority(user.getUserProperties().getRole())));

        Authentication authentication=authenticationProvider.authenticate(token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        // Create a new session and add the security context.

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT",context);

        return token;
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

