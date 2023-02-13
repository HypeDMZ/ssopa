package com.example.demo.Controller;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
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
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Api(tags = "PostController : 글 관련 요청 관련 컨트롤러")
public class PostController {

    private final PostService postService;


    @PostMapping("/add")
    @ApiOperation(value = "글 쓰기 요청")
    public ResponseEntity<PostResponseDto> NewPost(@RequestBody PostRequestDto request) {
        return ResponseEntity.ok(postService.newpost(request.getTitle(), request.getContent(), request.getCategory()));
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "게시글 수정하기")
    @ApiResponse(
            code = 403
            , message = "게시글 수정 권한이 없습니다."
    )
    public ResponseEntity<PostUpdateDto> updatepost(@RequestBody PostUpdateDto request, @PathVariable(name = "id") Long id) {
        try{
            return ResponseEntity.ok(postService.updatepost(request.getTitle(), request.getContent(), id));
        }catch (NoSufficientPermissionException e){
            return ResponseEntity.status(403).build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "게시글 정보 불러오기")
    public ResponseEntity<PostReadDto> ReadPost(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.readpost(id));
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "게시글 지우기 불러오기")
    @ApiResponse(
            code = 403
            , message = "게시글 삭제 권한이 없습니다."
    )
    public ResponseEntity<PostDeleteDto> DeletePost(@PathVariable(name = "id") Long id) {
        try{
            return ResponseEntity.ok(postService.deletepost(id));
        }catch (NoSufficientPermissionException e){
            return ResponseEntity.status(403).build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/load/{category}")
    @ApiOperation(value = "게시글 불러오기")
    @ApiResponse(
            code = 403
            , message = "게시글 삭제 권한이 없습니다."
    )
    public ResponseEntity<List<LoadDto>> LoadPost(@PathVariable(name = "category") String category) {
        return ResponseEntity.ok(postService.loadpost(category));
    }
}