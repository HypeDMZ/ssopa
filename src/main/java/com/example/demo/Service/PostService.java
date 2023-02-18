package com.example.demo.Service;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.post.*;
import com.example.demo.entity.Heart;
import com.example.demo.entity.Member;
import com.example.demo.entity.Post;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.HeartRepository;
import com.example.demo.repository.LoadPostRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final LoadPostRepository loadPostRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public PostResponseDto newpost(String title, String content, String category) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = Post.builder()
                .title(title)
                .writer(member.getNickname())
                .content(content)
                .category(category)
                .created_date(LocalDateTime.now())
                .modified_date(LocalDateTime.now())
                .deleteYn(Boolean.FALSE)
                .noticeYn(Boolean.FALSE)
                .view_cnt(0)
                .user_id(member.getId())
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

    @Transactional
    public PostDeleteDto deletepost (Long id) throws RuntimeException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
        // post 데이터베이스 지우기
        if (post.getUser_id() == member.getId()) {
            postRepository.delete(post);
            return PostDeleteDto.of(post);
        } else {
            throw new NoSufficientPermissionException();
        }
    }

    @Transactional
    public PostUpdateDto updatepost(String title, String content, Long id)  {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
        // 저자 일치 확인 -> 아니면 error

        if (post.getUser_id() == member.getId()) {
            post.updateValue(title, content,LocalDateTime.now());
            return PostUpdateDto.of(post);
        } else {
            throw new NoSufficientPermissionException();
        }
    }

    @Transactional
    public List<LoadDto> loadpost (String category) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());

        List<LoadDto> loadDtoList = Collections.emptyList();
        if (loadPostRepository.existsPostByCategory(category)) {
            loadDtoList = loadPostRepository.findAllByCategory(category);
        }
        else {
            throw new NoSufficientPermissionException();
        }
        return loadDtoList;
    }

    @Transactional
    public HeartDto heartpost(Long post_id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if(heartRepository.existsHeartByPostIdAndUserId(post_id, member.getId())){
            Heart heart = heartRepository.findByPostIdAndUserId(post_id, member.getId());
            heartRepository.delete(heart);
            return HeartDto.of(heart);
        }
        else{
            Heart heart = Heart.builder()
                    .postId(post_id)
                    .userId(member.getId())
                    .build();
            heartRepository.save(heart);
            return HeartDto.of(heart);
        }
    }
}
