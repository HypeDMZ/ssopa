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
public class CommentResponseDto {
    private long Id;
    private String nickname;
    private String comment;
    private String createdAt;
  //  private boolean isWritten;

    //댓글의 id, 작성자 이름, 댓글 내용, 생성일, 작성여부가 포함되어 있다.
    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .Id(comment.getId())
              //  .nickname(comment.getMember().getNickname())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedDate())
              //  .isWritten(bool)
                .build();
    }
}