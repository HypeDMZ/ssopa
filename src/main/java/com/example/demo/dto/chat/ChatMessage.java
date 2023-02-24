package com.example.demo.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "room_id")
    private String roomId;

    private String sender;

    private String message;

    private LocalDateTime time;

    public ChatMessage(MessageType type, String roomId, String sender, String message) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.time = LocalDateTime.now();
    }
}
