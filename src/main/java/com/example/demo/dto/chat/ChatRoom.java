package com.example.demo.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "ChatRoom")
@Entity
public class ChatRoom {

    @Id
    private String roomId;
    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private Long founder_id;

    @Column(nullable = false)
    private LocalDateTime created_Date;


    public static ChatRoom create(String name, Long founder_id) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        room.founder_id = founder_id;
        room.created_Date=LocalDateTime.now();
        return room;
    }
}