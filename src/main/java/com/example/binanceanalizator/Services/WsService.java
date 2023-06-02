package com.example.binanceanalizator.Services;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Service
@Log4j2
public class WsService {
@EventListener
    public void handleSubscriptions(SessionSubscribeEvent subscribeEvent){
    log.info("new subscriber : "+subscribeEvent.toString());
}
}
