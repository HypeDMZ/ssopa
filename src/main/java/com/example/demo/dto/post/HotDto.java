package com.example.demo.dto.post;


import com.example.demo.entity.Hot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotDto {
    private Long id;
    private int weight;
    private Long userId;

    public HotDto(Hot hot) {
        this.id = hot.getId();
        this.weight = hot.getWeight();
        this.userId = hot.getUserId();
    }
}
