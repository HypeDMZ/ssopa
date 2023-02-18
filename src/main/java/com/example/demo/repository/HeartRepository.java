package com.example.demo.repository;

import com.example.demo.entity.Heart;
import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsHeartByPostIdAndUserId(Long postId, Long userId);
    Heart findByPostIdAndUserId(Long postId, Long userId);
}
