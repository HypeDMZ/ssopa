package com.example.demo.dto.post;

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
    private String title;
    private String writer;
    private String content;
    private int view_cnt;
    private boolean noticeYn;
    private boolean deleteYn;
    private LocalDateTime modified_date;
    private LocalDateTime created_date;

    public static LoadDto of(Post post) {
        return LoadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .writer(post.getWriter())
                .content(post.getContent())
                .view_cnt(post.getView_cnt())
                .noticeYn(post.getNoticeYn())
                .modified_date(post.getModifiedDate())
                .created_date(post.getCreated_date())
                .deleteYn(post.getDeleteYn())
                .build();
    }
}
