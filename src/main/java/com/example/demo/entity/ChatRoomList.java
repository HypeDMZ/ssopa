package com.example.demo.entity;


import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class ChatRoomList {
    @Id
    private String randomId; //방 고유번호

    @Column(nullable = false,unique = false)// 만들어진 시간 날
    private LocalDateTime created_date;

    @Column(nullable = false,unique = false)
    private String roomName; //방 이름

    @Column(nullable = false,unique = false)
    private int participant_count;

    @Column(nullable = true,unique= true)
    private String chatlog; //채팅 로그

    @Column(nullable = false,unique = false)
    private Long founder_id; //방장 아이디



}
