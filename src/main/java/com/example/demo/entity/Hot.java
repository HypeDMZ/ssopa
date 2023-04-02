package com.example.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
public class Hot {
    public Hot() {
    }
    @Builder
    public Hot(Long id, Post  post, int weight, Long userId){
        this.id = id;
        this.post = post;
        this.weight = weight;
        this.userId = userId;
    }
    @Id
    @GeneratedValue
    @Column()
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;
    @Column(name = "weight", nullable = false)
    private int weight;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
