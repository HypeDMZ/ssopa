package com.example.demo.dto;

import com.example.demo.entity.Member;
import com.example.demo.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadDto {

    private Long id;

    public static LoadDto of(Post post) {
        return LoadDto.builder()
                .id(post.getId())
                .build();
    }
}
