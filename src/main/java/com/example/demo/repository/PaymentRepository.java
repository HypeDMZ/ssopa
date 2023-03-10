package com.example.demo.repository;

import com.example.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUserId(Long memberId);

    Optional<Payment> findByOrderIdAndUserId(String orderId, Long memberId);

    Optional<Payment> findByOrderId(String orderId);
}