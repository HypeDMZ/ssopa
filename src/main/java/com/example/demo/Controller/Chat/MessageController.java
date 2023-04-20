package com.example.demo.Controller.Chat;

import com.example.demo.dto.chat.ChatMessage;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    private final MemberRepository memberRepository;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message, @Header("sender") String sender) {
        Optional<Member> member = memberRepository.findById(Long.parseLong(sender));
        sender = member.get().getNickname();

        message.setSender(sender);
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(sender + "님이 입장하였습니다.");
            //message.setTime(LocalDateTime.now());
        }

        message.setTime(LocalDateTime.now());
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
    }
}//