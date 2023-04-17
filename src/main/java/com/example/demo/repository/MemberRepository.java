package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhonenumber(String phonenumber);

    Optional<Member> findByNameAndPhonenumber(String name, String phonenumber);

    Optional<Member> findById(Long id);

    boolean existsByPhonenumber(String phonenumber);

}

