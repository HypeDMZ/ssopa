package com.example.demo.repository;

import com.example.demo.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberReportRepository extends JpaRepository<UserReport, Long> {
    boolean existsByReporterIdAndReportedUserId(Long reporterId, Long reportedUserId);

    List<UserReport> findByReportedUserId(Long reportedId);

}