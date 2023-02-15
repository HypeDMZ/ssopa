package com.example.demo.entity;


import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class ChatRoomEntity {
    @Id
    private String randomId; //방 고유번호

    @Column(nullable = false,unique = false) //유저 아이디
    private Long id;




}
