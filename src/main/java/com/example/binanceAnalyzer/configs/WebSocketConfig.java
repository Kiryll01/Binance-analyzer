package com.example.binanceAnalyzer.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE+99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    public static final String REGISTRY="/ws";
    public static final String TOPIC_DESTINATION_PREFIX="/topic";
    public static final String QUEUE_DESTINATION_PREFIX="/queue";
    public static final String APPLICATION_DESTINATION_PREFIX="/app";
    public static final String USER_DESTINATION_PREFIX="/user";
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC_DESTINATION_PREFIX, QUEUE_DESTINATION_PREFIX);
        config.setApplicationDestinationPrefixes(APPLICATION_DESTINATION_PREFIX);
        config.setUserDestinationPrefix(USER_DESTINATION_PREFIX);
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(REGISTRY).setAllowedOrigins("*").withSockJS();
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor= MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                if(StompCommand.CONNECT.equals(accessor.getCommand())){
//                   Authentication authentication= (Authentication) accessor.getUser();
//                accessor.setUser(authentication);
//                }
//                return message;
//            }
//        });
//    }
}