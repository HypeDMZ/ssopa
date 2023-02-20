package com.example.demo.Controller;

import java.util.*;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.Service.CommentService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.dto.Comment.CommentDeleteDto;
import com.example.demo.dto.Comment.CommentRequestDto;
import com.example.demo.dto.Comment.CommentResponseDto;

import com.example.demo.dto.Message.MessageDto;
import com.example.demo.dto.Comment.LoadCommentDto;
import com.example.demo.dto.post.PostDeleteDto;
import com.example.demo.dto.post.PostRequestDto;
import com.example.demo.dto.post.PostResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/comment")
@Api(tags = "CommentController : 댓글 관리 컨트롤러")
@RequiredArgsConstructor

public class CommentController {
    private final CommentService commentService;
    private final HttpResponseUtil httpResponseUtil;

    @GetMapping("/list/{postId}")
    @Operation(summary = "댓글 목록 불러오기")
    public ResponseEntity<?> LoadComment(@PathVariable(name = "postId") Long postId) {
        try {
            return httpResponseUtil.createOKHttpResponse(commentService.loadComment(postId), "댓글 목록 불러오기 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("댓글 목록 불러오기 실패: " + e.getMessage());
        }
    }
    @PostMapping("/write")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto request) {
        try {
            return httpResponseUtil.createOKHttpResponse(commentService.createComment(request.getId(), request.getComment()), "댓글 작성 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("댓글 작성 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "댓글 삭제")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> removeComment(@PathVariable(name = "id") Long id) {
        try {
            return httpResponseUtil.createOKHttpResponse(commentService.removeComment(id), "댓글 삭제 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("댓글 삭제 실패: " + e.getMessage());
        }
    }
}