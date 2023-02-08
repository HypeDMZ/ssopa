package com.example.demo.Service;

import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.*;
import com.example.demo.entity.Member;
import com.example.demo.entity.Post;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;

    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Transactional
    public PostResponseDto newpost(String title, String content) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = Post.builder()
                .title(title)
                .writer(member.getNickname())
                .content(content)
                .created_date(LocalDateTime.now())
                .modified_date(LocalDateTime.now())
                .deleteYn(Boolean.FALSE)
                .noticeYn(Boolean.FALSE)
                .view_cnt(0)
                .build();
        return PostResponseDto.of(postRepository.save(post));
    }

    @Transactional
    public PostReadDto readpost (Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));

        return PostReadDto.of(post);
    }


}
