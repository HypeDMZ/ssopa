package com.example.demo.Controller;

import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.Service.PostService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.dto.post.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Api(tags = "PostController : 글 관련 요청 관련 컨트롤러")
public class PostController {
    private final PostService postService;
    private final HttpResponseUtil httpResponseUtil;

    @PostMapping("/add")
    @ApiOperation(value = "글 쓰기 요청")
    public ResponseEntity<?> NewPost(@RequestBody PostRequestDto request) {
        return httpResponseUtil.createOKHttpResponse(postService.newpost(request.getTitle(), request.getContent(), request.getCategory()), "글 작성 성공");
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "게시글 수정하기")
    public ResponseEntity<?> updatepost(@RequestBody PostUpdateDto request, @PathVariable(name = "id") Long id) {
        return httpResponseUtil.createOKHttpResponse(postService.updatepost(request.getTitle(), request.getContent(), id), "게시글 수정 성공");
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "게시글 정보 불러오기")
    public ResponseEntity<?> ReadPost(@PathVariable(name = "id") Long id) {
        return httpResponseUtil.createOKHttpResponse(postService.readpost(id), "게시글 정보 불러오기");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "게시글 지우기")
    public ResponseEntity<?> DeletePost(@PathVariable(name = "id") Long id) {
        return httpResponseUtil.createOKHttpResponse(postService.deletepost(id), "게시글 삭제 성공");
    }

    @GetMapping("/load/{category}")
    @ApiOperation(value = "게시글 불러오기")
    public ResponseEntity<?> LoadPost(@PathVariable(name = "category") String category) {
        return httpResponseUtil.createOKHttpResponse(postService.loadpost(category), "게시글 목록 불러오기");
    }

    @GetMapping("/load/my")
    @ApiOperation(value = "내가 쓴 게시글 불러오기")
    public ResponseEntity<?> LoadMyPost() {
        return httpResponseUtil.createOKHttpResponse(postService.myWritePost(), "내가 쓴 게시글 불러오기");
    }

    @PostMapping("/heart/{id}")
    @ApiOperation(value = "게시글 좋아요 누르기")
    public ResponseEntity<?> HeartPost(@PathVariable(name = "id") Long id) {
        return httpResponseUtil.createOKHttpResponse(postService.heartpost(id), "게시글 좋아요 누르기");
    }
}