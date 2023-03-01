package com.example.demo.Service;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.chat.ChatMessage;
import com.example.demo.dto.chat.ChatRoom;
import com.example.demo.dto.post.LoadDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private Map<String, ChatRoom> chatRooms;
    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final MemberRepository memberRepository;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
        chatRoomRepository.findAll().forEach(chatRoom -> {
            chatRooms.put(chatRoom.getRoomId(), chatRoom);
        });
    }

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);
        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name,11313l);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public List<ChatMessage> loadchat(String roomId,int page){


        PageRequest pageRequest = PageRequest.of(page, 40, Sort.by("time").descending());
        Page<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId,pageRequest);
        List<ChatMessage> messages=chatMessages.getContent();

        messages.parallelStream().forEach(chatMessage -> {
            Optional<Member> member = memberRepository.findById(Long.parseLong(chatMessage.getSender()));
            if(member.isPresent()){
                chatMessage.setSender(member.get().getNickname());
            }else{
                chatMessage.setSender("탈퇴한 회원");
            }

        });

        return messages;
    }
}