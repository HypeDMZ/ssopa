package com.example.demo.Service;

import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.chat.ChatRoom;
import com.example.demo.dto.chat.ChatRoomCreateRequest;
import com.example.demo.entity.ChatRoomEntity;
import com.example.demo.entity.Member;
import com.example.demo.repository.ChatRoomEntityRepository;
import com.example.demo.repository.ChatRoomListRepository;
import com.example.demo.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    private final ChatRoomListRepository chatRoomListRepository;

    private final ChatRoomEntityRepository chatRoomEntityRepository;

    private final MemberRepository memberRepository;


    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>(); //ChatService가 시작되고  채팅방 목록을 초기화 해준다.
        // 채팅방 목록을 DB에서 가져와서 채팅방 목록에 추가해준다.
        chatRoomListRepository.findAll().forEach(chatRoomList -> {
            ChatRoom chatRoom = ChatRoom.builder()
                    .roomId(chatRoomList.getRandomId())
                    .name(chatRoomList.getRoomName())
                    .build();
            chatRooms.put(chatRoomList.getRandomId(), chatRoom);
        });
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(ChatRoomCreateRequest request) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .founder_id(0l)
                .name(request.getTitle())
                .build();
        chatRooms.put(randomId, chatRoom);
        // 채팅방을 DB에 저장한다.
        chatRoomListRepository.save(chatRoom.toEntity());
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}