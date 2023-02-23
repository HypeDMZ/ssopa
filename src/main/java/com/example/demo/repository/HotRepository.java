package com.example.demo.repository;

import com.example.demo.entity.Hot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotRepository extends JpaRepository<Hot, Long> {

}
