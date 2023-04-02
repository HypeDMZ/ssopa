package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.Comment.CommentDeleteDto;
import com.example.demo.dto.Comment.CommentResponseDto;
import com.example.demo.dto.Comment.LoadCommentDto;
import com.example.demo.dto.post.PostResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Hot;
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
    private final ReportService reportService;
    private final SaveData saveData;

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

    @Transactional
    public List<LoadCommentDto> loadComment(Long id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));

        List<LoadCommentDto> loadDtoList;
        if (loadCommentRepository.existsByPostId(id)) {
            loadDtoList = loadCommentRepository.findAllByPostId(id);
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

        reportService.checkReport(member.getId()); // 신고당한 유저는 댓글 사용 제한

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));

        Comment comment = Comment.builder()
                .comment(content)
                .createdDate(LocalDateTime.now().toString())
                .userId(member.getId())
                .postId(post.getId())
                .build();
        Hot hot = Hot.builder()
                .post(post)
                .userId(member.getId())
                .weight(5)
                .build();
        saveData.saveData(hot);
        return CommentResponseDto.of(commentRepository.save(comment));
    }

}