package com.example.demo.Controller;

import java.util.*;
import com.example.demo.Service.CommentService;
import com.example.demo.dto.Comment.CommentRequestDto;
import com.example.demo.dto.Comment.CommentResponseDto;

import com.example.demo.dto.Message.MessageDto;
import io.swagger.annotations.Api;
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

    @Operation(summary = "댓글 리스트")
    @ApiResponse(code = 200, message = "댓글 목록 불러오기")
    @GetMapping("/list")
    public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @Operation(summary = "댓글 달기")
    @ApiResponse(code = 200, message = "댓글 달기 성공")
    @PostMapping("/writecomment")
    public ResponseEntity<CommentResponseDto> postComment(@RequestBody CommentRequestDto request) {
        return ResponseEntity.ok(commentService.createComment(request.getId(), request.getBody()));
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponse(code = 200, message = "댓글 삭제 성공")
    @PostMapping("/one")
    public ResponseEntity<MessageDto> deleteComment(@RequestParam(name = "id") Long id) {
        commentService.removeComment(id);
        return ResponseEntity.ok(new MessageDto("Success"));
    }
}