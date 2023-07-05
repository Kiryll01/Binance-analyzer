package com.example.binanceAnalyzer.configs;

import com.example.binanceAnalyzer.Controllers.Rest.AuthenticationController;
import com.example.binanceAnalyzer.Controllers.Rest.UserController;
import com.example.binanceAnalyzer.Models.Entities.InMemory.IdSessionIdUser;
import com.example.binanceAnalyzer.Models.Entities.InMemory.RedisUser;
import com.example.binanceAnalyzer.Services.UserService;
import com.example.binanceAnalyzer.repos.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UsersRepo usersRepo;
    private final RedisTemplate<String, RedisUser> redisTemplate;
    private final HashOperations<String,String, RedisUser> userHashOperations;
    private final HashOperations<String,String, IdSessionIdUser> idUserHashOperations;
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService(usersRepo,redisTemplate,userHashOperations,idUserHashOperations);
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }
    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher){
    return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
}
    @Bean
    public PasswordEncoder passwordEncoder(){
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .passwordEncoder(passwordEncoder());
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(AuthenticationController.SIGN_UP_DESTINATION).permitAll()
                .requestMatchers(AuthenticationController.SIGN_IN_DESTINATION).anonymous()
                .requestMatchers(UserController.GET_ACCOUNT_INFORMATION).fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }

}
