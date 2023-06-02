package com.example.binanceanalizator.configs;

import com.binance.api.client.BinanceApiWebSocketClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

@Configuration
public class WebClientConfig {


//    @Bean
//    WebSocketStompClient webClient(){
//
//        WebSocketStompClient client=new WebSocketStompClient(new StandardWebSocketClient());
//
//
//client.setMessageConverter(new MappingJackson2MessageConverter());
//
//client.connectAsync("wss://stream.binance.com:9443/ws",testSessionHandler());
//
//return client;
//    }
@Bean
TestSessionHandler testSessionHandler(){
return new TestSessionHandler();
}
}

@AllArgsConstructor
@Getter
@Log4j2
class TestSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        //session.subscribe(SUBSCRIPTION_TOPIC, this);
        log.info("connection success");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.warn("Stomp Error:", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        super.handleTransportError(session, exception);
        log.warn("Stomp Transport Error:", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        log.info(headers.toString());
        return byte[].class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        log.info("Handle Frame with payload: {}", o);
    }
}