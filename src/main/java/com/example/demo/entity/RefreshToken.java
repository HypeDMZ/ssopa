package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
@Entity
@Builder
public class RefreshToken {

    @Id
    private String email;

    @Column(nullable = false)
    private String value;

    public void updateValue(String token) {
        this.value = token;
    }


}