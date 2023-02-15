package com.example.demo.Controller;

import com.example.demo.Service.ChatService;
import com.example.demo.dto.chat.ChatRoom;
import com.example.demo.dto.chat.ChatRoomCreateRequest;
import com.example.demo.dto.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping
    public ChatRoom createRoom(@RequestBody ChatRoomCreateRequest request) {
        return chatService.createRoom(request);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}