package com.example.demo.config;

import com.example.demo.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration  // Indicates that this class provides bean definitions and is processed by the Spring container
@EnableWebSocketMessageBroker  // Enables WebSocket message handling and the creation of a WebSocket message broker
@RequiredArgsConstructor  // Generates a constructor that injects any final fields with dependencies
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;  // Injects an instance of the StompHandler class

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")  // Registers the endpoint /ws/chat for WebSocket communication
                .setAllowedOriginPatterns("*")  // Allows connections from any origin
                .withSockJS();  // Enables the use of the SockJS fallback option in case WebSocket is not available
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {


        registry.enableSimpleBroker("/queue", "/topic");


        registry.setApplicationDestinationPrefixes("/app");  // Sets the prefix used to filter messages targeted for application handling
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);  // Configures the client inbound channel to add an interceptor, which in this case is an instance of the StompHandler class
    }
}
