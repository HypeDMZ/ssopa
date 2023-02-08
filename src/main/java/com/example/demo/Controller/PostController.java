package com.example.demo.Controller;

import com.example.demo.Service.MemberService;
import com.example.demo.Service.PostService;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.*;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Api(tags = "PostController : 글 관련 요청 관련 컨트롤러")
public class PostController {

    private final PostService postService;


    @PostMapping("/add")
    @ApiOperation(value = "글 쓰기 요청")
    public ResponseEntity<PostResponseDto> NewPost(@RequestBody PostRequestDto request) {
        return ResponseEntity.ok(postService.newpost(request.getTitle(), request.getContent()));
    }



}