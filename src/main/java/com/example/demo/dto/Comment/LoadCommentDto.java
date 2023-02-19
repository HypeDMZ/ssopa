package com.example.demo.dto.Comment;

import com.example.demo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadCommentDto {

    private Long id;
    private String comment;
    private Long userId;
    private Long postId;
    private String createDate;

    public static LoadCommentDto of(Comment comment) {
        return LoadCommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .createDate(comment.getCreatedDate())
                .build();
    }
}
