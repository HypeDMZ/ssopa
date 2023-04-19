package com.example.demo.repository;

import com.example.demo.entity.DeviceToken;
import com.example.demo.entity.Heart;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken,Long> {

    boolean existsByToken(String token);

    List<DeviceToken> findAllByMemberIdIsNullAndTokenEquals(String token);

    Optional<DeviceToken> findByToken(String token);

    Optional<DeviceToken> findByTokenAndMemberIdIsNotNull(String token);

    List<DeviceToken> findAllByMemberId(Member member);
}
