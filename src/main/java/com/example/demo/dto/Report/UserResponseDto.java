package com.example.demo.dto.Report;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private Long reporterId; // 신고자
    private Long reportedUserId; // 신고당한 사람
    private String content; // 신고 내용
    private LocalDateTime createdAt;

    public UserResponseDto(Long reporterId, Long reportedUserId, String content){
        this.reporterId = reporterId;
        this.reportedUserId = reportedUserId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }


}