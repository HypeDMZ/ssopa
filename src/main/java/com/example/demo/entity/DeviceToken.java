package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;


@Getter //lombok
@Setter
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;


    @Column(nullable = false)
    private Boolean isRegistered;

    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "id", nullable = true)
    private Member memberId;

    public void setMember(Member member) {
        this.memberId = member;
    }
}
