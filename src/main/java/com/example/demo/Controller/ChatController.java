package com.example.demo.Controller;

import com.example.demo.Service.ChatService;
import com.example.demo.dto.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestBody String name) {
        return chatService.createRoom(name);
    }

    @GetMapping("/rooms")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}