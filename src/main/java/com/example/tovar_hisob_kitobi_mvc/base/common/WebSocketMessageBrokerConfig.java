package com.example.tovar_hisob_kitobi_mvc.base.common;

import com.example.tovar_hisob_kitobi_mvc.implementation.home.controller.HomeController;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {
    public static final String _prefix="/test";
    public static final String _prixod=_prefix+"/"+ HomeController._prixodSumma;
    public static final String _prixodByCreated=_prefix+"/"+HomeController._prixodSummaByCreated;
    public static final String _rasxod=_prefix+"/"+HomeController._rasxodSumma;
    public static final String _rasxodByCreated=_prefix+"/"+HomeController._rasxodSummaByCreated;
    public static final String _vozvrat=_prefix+"/"+HomeController._vozvratSumma;
    public static final String _vozvratByCreated=_prefix+"/"+HomeController._vozvratSummaByCreated;

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry){
        registry.addEndpoint("/websocket").withSockJS();
    }
    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry){
        registry.enableSimpleBroker(_prefix);
        registry.setApplicationDestinationPrefixes("/web");
    }
}
