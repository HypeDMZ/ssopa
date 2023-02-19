package com.example.demo.repository;

import com.example.demo.dto.Comment.LoadDto;
import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface LoadCommentRepository extends JpaRepository<Comment, String> {

    List<LoadDto> findAllById(Long Id);

    boolean existsById(Long id);

}
