package com.example.demo.repository;

import com.example.demo.dto.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findByroomId(String roomId); //방 아이디로 찾기(중복방지

    Optional<ChatRoom> findByRoomName(String roomName); //방 이름으로 찾기

}
