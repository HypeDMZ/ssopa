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
public class PostDeleteDto {
    @ApiModelProperty(value="글제목", example="오늘의 공지(삭제된 글의 제목)",required = true)
    private String title;

    public static PostDeleteDto of(Post post) {
        return PostDeleteDto.builder()
                .title(post.getTitle())
                .build();
    }
}