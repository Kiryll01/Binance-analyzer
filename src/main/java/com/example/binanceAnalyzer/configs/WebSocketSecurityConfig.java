package com.example.binanceAnalyzer.configs;

import com.example.binanceAnalyzer.Controllers.Ws.StatsWsController;
import com.example.binanceAnalyzer.Models.Plain.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    public static final String POSTFIX="/**";
    @Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        return messages.simpDestMatchers(StatsWsController.FETCH_MA_STATS).hasAnyAuthority(Roles.FETCH_STATS.getValue(),Roles.FULLY_CONFIGURED.getValue(),
                Roles.ADMIN.getValue())
                .simpSubscribeDestMatchers(StatsWsController.FETCH_TICKER_STATS).permitAll()
                .simpTypeMatchers(CONNECT,CONNECT_ACK,DISCONNECT,DISCONNECT_ACK).permitAll()
                .build();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
