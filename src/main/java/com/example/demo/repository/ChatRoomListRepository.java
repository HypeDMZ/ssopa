package com.example.demo.repository;

import com.example.demo.entity.ChatRoomEntity;
import com.example.demo.entity.ChatRoomList;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomListRepository extends JpaRepository<ChatRoomList, String> {
    Optional<ChatRoomList> findByRandomId(String randomId); //방 고유번호로 찾기

    Optional<ChatRoomList> findByRoomName(String roomName); //방 이름으로 찾기

}

