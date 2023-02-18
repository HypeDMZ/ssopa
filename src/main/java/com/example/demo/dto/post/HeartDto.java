package com.example.demo.dto.post;

import com.example.demo.entity.Heart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeartDto {
    private Long postId;
    private Long userId;
    public static HeartDto of(Heart heart) {
        return HeartDto.builder()
                .postId(heart.getPostId())
                .userId(heart.getUserId())
                .build();
    }
}
