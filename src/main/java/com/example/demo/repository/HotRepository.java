package com.example.demo.repository;

import com.example.demo.entity.Hot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotRepository extends JpaRepository<Hot, Long> {
    Optional<Hot> findByPostIdAndUserId(Long postId, Long userId);

}
