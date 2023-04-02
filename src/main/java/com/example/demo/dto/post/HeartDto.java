package com.example.demo.dto.post;

import com.example.demo.entity.Heart;
import com.example.demo.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeartDto {
    private Post post;
    private Long userId;
    public static HeartDto of(Heart heart) {
        return HeartDto.builder()
                .post(heart.getPost())
                .userId(heart.getUserId())
                .build();
    }
}
