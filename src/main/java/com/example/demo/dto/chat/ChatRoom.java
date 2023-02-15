package com.example.demo.dto.chat;

import com.example.demo.Service.ChatService;
import com.example.demo.config.SecurityUtil;
import com.example.demo.entity.ChatRoomEntity;
import com.example.demo.entity.ChatRoomList;
import com.example.demo.entity.Member;
import com.example.demo.repository.ChatRoomEntityRepository;
import com.example.demo.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class ChatRoom {
    private String roomId;
    private String name;

    private Long founder_id;

    private LocalDateTime created_Date;
    private Set<WebSocketSession> sessions = new HashSet<>();



    @Builder
    public ChatRoom(String roomId, String name, Long founder_id){
        this.roomId = roomId;
        this.name = name;
        this.founder_id=founder_id;
        this.created_Date= LocalDateTime.now();
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);

    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }

    public ChatRoomList toEntity() {
        return ChatRoomList.builder()
                .randomId(roomId)
                .roomName(name)
                .founder_id(founder_id)
                .created_date(created_Date)
                .build();
    }
}