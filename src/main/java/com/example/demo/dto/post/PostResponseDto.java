package com.example.demo.dto.post;


import com.example.demo.entity.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {
    @ApiModelProperty(value="글제목", example="오늘의 공지",required = true)
    private String title;

    @ApiModelProperty(value="글 고유 id", example="18",required = true)
    private Long id;

    @ApiModelProperty(value="글카테고리", example="게시판 이름(ex 뜨밤)",required = true)
    private String category;

    @ApiModelProperty(value="글내용", example="내 용",required = true)
    private String content;

    @ApiModelProperty(value="작성자", example="작성자",required = true)
    private String writer;


    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory().toString())
                .content(post.getContent())
                .writer(post.getWriter())
                .build();
    }

}