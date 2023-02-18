package com.example.demo.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.Comment.CommentResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Member;
import com.example.demo.entity.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;


    public List<CommentResponseDto> getComment(Long id) {

        List<Comment> comments = commentRepository.findAllByPostsId(id);
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            return comments
                    .stream()
                    .map(comment -> CommentResponseDto.of(comment, false))
                    .collect(Collectors.toList());
        } else {
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
            Map<Boolean, List<Comment>> collect = comments.stream()
                    .collect(
                            Collectors.partitioningBy(
                                    comment -> comment.getMember().equals(member)
                            )
                    );
            List<CommentResponseDto> tCollect = collect.get(true).stream()
                    .map(t -> CommentResponseDto.of(t, true))
                    .collect(Collectors.toList());
            List<CommentResponseDto> fCollect = collect.get(false).stream()
                    .map(f -> CommentResponseDto.of(f, false))
                    .collect(Collectors.toList());

            return Stream
                    .concat(tCollect.stream() ,fCollect.stream())
                    .sorted(Comparator.comparing(CommentResponseDto::getId))
                    .collect(Collectors.toList());
        }
    }

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long id, String text) {
        Member member = memberRepository.findById(
                        SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 없습니다."));

        Comment comment = Comment.builder()
                .comment(text)
                .posts(post)
                .member(member)
                .build();

        return CommentResponseDto.of(commentRepository.save(comment), true);

    }

    // 댓글 삭제
    @Transactional
    public void removeComment(Long id) {
        Member member = memberRepository.findById(
                        SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 하십시오"));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
        if (!comment.getMember().equals(member)) {
            throw new RuntimeException("작성자와 로그인이 일치하지 않습니다.");
        }
        commentRepository.delete(comment);
    }
}