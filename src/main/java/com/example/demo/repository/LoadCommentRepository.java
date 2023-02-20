package com.example.demo.repository;

import com.example.demo.dto.Comment.LoadCommentDto;
import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LoadCommentRepository extends JpaRepository<Comment, Long> {

    List<LoadCommentDto> findAllByPostId(Long post_id);

    boolean existsByPostId(Long post_id);

    List<LoadCommentDto> findAllByUserId(Long user_id);

    boolean existsByUserId(Long user_id);

}