package com.example.demo.repository;

import com.example.demo.entity.ChatRoomEntity;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomEntityRepository extends JpaRepository<ChatRoomEntity, String> {
    Optional<ChatRoomEntity> findByRandomId(String randomId); //방 고유번호로 찾기

}

