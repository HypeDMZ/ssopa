package com.example.demo.repository;

import com.example.demo.dto.chat.ChatMessage;
import com.example.demo.dto.post.LoadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRoomId(String roomId);

    Page<ChatMessage> findByRoomId(String room_id, Pageable pageable);
}
