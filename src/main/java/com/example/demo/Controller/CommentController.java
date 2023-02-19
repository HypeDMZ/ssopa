package com.example.demo.Controller;

import java.util.*;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.Service.CommentService;
import com.example.demo.dto.Comment.CommentDeleteDto;
import com.example.demo.dto.Comment.CommentRequestDto;
import com.example.demo.dto.Comment.CommentResponseDto;

import com.example.demo.dto.Message.MessageDto;
import com.example.demo.dto.Comment.LoadDto;
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

    @Operation(summary = "댓글 리스트")
    @ApiResponse(code = 200, message = "댓글 목록 불러오기")
    @GetMapping("/list")
    public ResponseEntity<List<LoadDto>> getComments(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(commentService.getComment(id));
   }

    @PostMapping("/write")
    @ApiOperation(value = "댓글 달기 요청")
    // ssopa02.com/post/add
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto request) {
        return ResponseEntity.ok(commentService.createComment(request.getId(), request.getComment()));
    }

    @Operation(summary = "댓글 삭제")
    @GetMapping("/delete/{id}")
    @ApiOperation(value = "게시글 지우기 불러오기")
    @ApiResponse(
            code = 403
            , message = "게시글 삭제 권한이 없습니다."
    )
    public ResponseEntity<CommentDeleteDto> removeComment(@RequestParam(name = "id") Long id) {
        try{
            return ResponseEntity.ok(commentService.removeComment(id));
        }catch (NoSufficientPermissionException e){
            return ResponseEntity.status(403).build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

}