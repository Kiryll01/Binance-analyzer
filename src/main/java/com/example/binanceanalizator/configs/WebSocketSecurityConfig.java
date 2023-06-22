package com.example.binanceanalizator.configs;

import com.example.binanceanalizator.Models.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig {
    public static final String POSTFIX="/**";
    @Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages.simpDestMatchers(WebSocketConfig.APPLICATION_DESTINATION_PREFIX+POSTFIX).authenticated()
                .simpSubscribeDestMatchers(WebSocketConfig.TOPIC_DESTINATION_PREFIX+POSTFIX)
                .hasAnyAuthority(Roles.FULLY_CONFIGURED.getValue(),Roles.ADMIN.getValue(),Roles.FETCH_STATS.getValue())
                .simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT).permitAll()
                .simpTypeMatchers(MESSAGE,SUBSCRIBE).authenticated()
                .anyMessage().authenticated();
        return messages.build();
    }


}
