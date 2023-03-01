package com.example.demo.dto.post;


import com.example.demo.entity.Hot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotDto {
    private Long postId;
    private int weight;

    public HotDto(Hot hot) {
        this.postId = hot.getPostId();
        this.weight = hot.getWeight();
    }
}
