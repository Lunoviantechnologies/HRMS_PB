package com.example.Notification;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class UserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String email = accessor.getFirstNativeHeader("email");

            if (email != null) {

                System.out.println("STOMP CONNECT header email: " + email);

                accessor.setUser(new StompPrincipal(email));

                System.out.println("Principal set: " + accessor.getUser().getName());

            }

        }

        return message;
    }
}