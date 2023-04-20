package com.example.demo.handler;

import com.example.demo.dto.chat.ChatMessage;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final TokenProvider jwtService;
    private static final String BEARER_PREFIX = "Bearer ";

    private final ChatMessageRepository chatMessageRepository;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);



//        // 헤더 토큰 얻기
//        String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
//        authorizationHeader = authorizationHeader.substring(1, authorizationHeader.length()-1);
//        // 토큰 자르기
//
//        if(authorizationHeader == null || authorizationHeader.equals("null")){
//            throw new MessageDeliveryException("메세지 예외");
//        }
//
//        String token = authorizationHeader.substring(BEARER_PREFIX.length());
//        // 토큰 검증  테스트 용으로 꺼둠
//        if(jwtService.validateToken(token)==false){
//            throw new RuntimeException("토큰 검증 실패");
//        }
//
//
//        //get nickname which is stored at member table by using token
//        String userId = jwtService.getMemberEmailByToken(token);

        String userId = "18";
        accessor.setNativeHeader("sender", userId);

        //메시지 타입이 메시지일때만 데이터베이스에 내용 저장
        if(headerAccessor.getCommand().getMessageType()==SimpMessageType.MESSAGE){
            String roomId ="";
            String content = "";

            // Extract the message from the payload of the STOMP message
            byte[] payload = (byte[]) message.getPayload();
            String messageContent = new String(payload, StandardCharsets.UTF_8);
            System.out.println(messageContent);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(messageContent);
                JsonNode valueNode = jsonNode.get("roomId");
                JsonNode valueNode2= null;
                roomId = valueNode.asText();
                try {
                    valueNode2 = jsonNode.get("message");
                    content = valueNode2.asText();
                    // Create a new ChatMessage object with the extracted information
                    ChatMessage chatMessage = new ChatMessage(ChatMessage.MessageType.TALK, roomId, userId, content);
                    // Persist the chat message to the database using your Spring Data JPA repository
                    chatMessageRepository.save(chatMessage);
                }catch (Exception e){
                    return message;
                }



            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


        }


        return message;
    }
}