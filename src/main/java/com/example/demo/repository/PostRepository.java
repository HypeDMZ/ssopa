package com.example.demo.repository;

import com.example.demo.entity.Member;
import com.example.demo.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {


}

