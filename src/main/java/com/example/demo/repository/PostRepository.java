package com.example.demo.repository;

import com.example.demo.dto.post.LoadDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    List<LoadDto> findAllByCategory(String category);

    // 게시글 총 갯수
    boolean existsPostByCategory(String category);

    // 내가 쓴 글
    List<LoadDto> findAllByUserId(Long user_id);

    // 페이징 기능
    Page<LoadDto> findByCategory(String category, Pageable pageable);
    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long id);


}

