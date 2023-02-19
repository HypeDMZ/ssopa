package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.Comment.CommentDeleteDto;
import com.example.demo.dto.Comment.CommentResponseDto;
import com.example.demo.dto.Comment.LoadDto;
import com.example.demo.dto.post.PostResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Member;
import com.example.demo.entity.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.LoadCommentRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    private final LoadCommentRepository loadCommentRepository;

    //댓글 삭제
    @Transactional
    public CommentDeleteDto removeComment(Long id) throws RuntimeException{
        Member member = memberRepository.findById((SecurityUtil.getCurrentMemberId())).orElseThrow(()->
                new RuntimeException("로그인 유저 정보가 없습니다."));
        Comment comment = commentRepository.findById(id).orElseThrow(()->new RuntimeException("댓글 정보가 없습니다."));

        if(comment.getUserId() == member.getId()) {
            commentRepository.delete(comment);
            return CommentDeleteDto.of(comment);
        }else{
            throw new NoSufficientPermissionException();
        }
    }

/*
    // 댓글 load
    @Transactional
    public List<LoadDto> getComment (Long id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());

        List<LoadDto> loadDtoList = Collections.emptyList();

        if (loadCommentRepository.existsById(id)) {
            loadDtoList = loadCommentRepository.findAllById(id);
        }
        else {
            throw new NoSufficientPermissionException();
        }
        return loadDtoList;
    }

 */

    @Transactional
    public List<LoadDto> loadComment(Long id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));

        List<LoadDto> loadDtoList;
        if (loadCommentRepository.existsById(post.getId())) {
            loadDtoList = loadCommentRepository.findAllById(post.getId());
        }
        else {
            throw new NoSufficientPermissionException();
        }
        return loadDtoList;
    }

    //댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long id, String content){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
        Comment comment = Comment.builder()
                .comment(content)
                .createdDate(LocalDateTime.now().toString())
                .userId(member.getId())
                .postId(post.getId())
                .build();
        return CommentResponseDto.of(commentRepository.save(comment));
    }

}