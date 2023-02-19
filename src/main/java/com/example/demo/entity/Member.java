package com.example.demo.entity;

import javax.persistence.*;
import lombok.*;

import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor


public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String phonenumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public Member(Long id, String email, String phonenumber, String password, String name, Authority authority) {
        this.id = id;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.name = name;
        this.authority = authority;
    }


}
