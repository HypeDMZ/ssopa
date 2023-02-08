package com.example.demo.dto;

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
public class PostReadDto {
    @ApiModelProperty(value="글제목", example="오늘의 공지",required = true)
    private String title;
    @ApiModelProperty(value="글내용", example="내 용",required = true)
    private String content;
    @ApiModelProperty(value="게시글 작성 시간", example="2002.02.24",required = true)
    private String created_date;

    @ApiModelProperty(value="최종 수정 날자", example="2002.02.24",required = true)
    private String modified_date;

    @ApiModelProperty(value="글 작성자", example="OpenAI",required = true)
    private String writer;

    @ApiModelProperty(value="조회수", example="5000",required = true)
    private int view_cnt;

    @ApiModelProperty(value="공지글 여부", example="yes",required = true)
    private boolean notice_yn;

    @ApiModelProperty(value="글 삭제 여부", example="삭제됨",required = true)
    private boolean delete_yn;

    public static PostReadDto of(Post post) {
        return PostReadDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .created_date(post.getCreated_date().toString())
                .modified_date(post.getModified_date().toString())
                .writer(post.getWriter())
                .view_cnt(post.getView_cnt())
                .notice_yn(post.getNoticeYn())
                .delete_yn(post.getDeleteYn())
                .build();
    }
}