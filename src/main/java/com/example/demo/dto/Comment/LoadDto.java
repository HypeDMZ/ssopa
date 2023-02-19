package com.example.demo.dto.Comment;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadDto {

    private Long id;
    private String comment;
    private Long userId;

    private Long postId;

    private String createDate;
    // private int view_cnt;
   // private boolean noticeYn;
   // private LocalDateTime modified_date;


    public static com.example.demo.dto.Comment.LoadDto of(Comment comment) {
        return com.example.demo.dto.Comment.LoadDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .createDate(comment.getCreatedDate())
                .build();
    }
}
