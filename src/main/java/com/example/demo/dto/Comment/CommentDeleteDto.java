package com.example.demo.dto.Comment;

import com.example.demo.entity.Comment;
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
public class CommentDeleteDto {
    @ApiModelProperty(value="댓글 내용", example="오늘의 공지(삭제된 댓글의 내용)",required = true)
    private String comment;

    public static com.example.demo.dto.Comment.CommentDeleteDto of(Comment comment) {
        return CommentDeleteDto.builder()
                .comment(comment.getComment())
                .build();
    }
}