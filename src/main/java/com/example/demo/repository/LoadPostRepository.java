package com.example.demo.repository;

import com.example.demo.dto.LoadDto;
import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface LoadPostRepository extends JpaRepository<Post, String> {

    // 게시글 목록
    List<LoadDto> findAllByCategory(String category);

    // 게시글 총 갯수
    boolean existsPostByCategory(String category);
}
