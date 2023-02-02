package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    public  int index;
    public  String name;
    public  String id;
    public  String password;
    public Long ID;

    public void setId(Long ID) {
        this.ID = ID;
    }

    @Id
    public Long getId() {
        return ID;
    }
}
