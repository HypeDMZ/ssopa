package com.example.demo.Service;
import com.example.demo.Exception.Report.AlreadyReportException;
import com.example.demo.Exception.Report.NotSelfReportException;
import com.example.demo.Exception.Member.MemberNotFoundException;
import com.example.demo.Exception.Report.ReportedUserException;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.Report.UserReportRequest;
import com.example.demo.dto.Report.UserResponseDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.UserReport;
import com.example.demo.repository.MemberReportRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@RequiredArgsConstructor
@Service
public class ReportService {
    public final MemberReportRepository memberReportRepository;
    public final MemberRepository memberRepository;

    @Transactional
    public UserResponseDto reportUser(UserReportRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 신고하는 사람
        Member reporter = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(MemberNotFoundException::new);
        System.out.println("로그인 정보 : "+reporter.getEmail());
        // 신고 당하는 사람
        Member reportedUser = memberRepository.findById(req.getReportedUserId()).orElseThrow(MemberNotFoundException::new);

        if(reporter.getId() == req.getReportedUserId()) {// 본인 신고시 exception 발생
            throw new NotSelfReportException();
        }

        if(memberReportRepository.existsByReporterIdAndReportedUserId(reporter.getId(),reportedUser.getId()) == false) {
            // 신고 한 적이 없다면, 테이블 생성 후 신고 처리 (ReportedUser의 User테이블 boolean 값 true 변경 ==> 신고처리)
            UserReport userReport = new UserReport(reporter.getId(), reportedUser.getId(), req.getContent());
            memberReportRepository.save(userReport);

            if(memberReportRepository.findByReportedUserId(req.getReportedUserId()).size() >= 10) {
                // 신고 수 10 이상일 시 true 설정 -> true / false 를 통해서 게시물 작성, 댓글 작성 등 권한 주기 or false시 로그인 막는 방법?
                reportedUser.setReported(true);
                reportedUser.setLastReportedDay(LocalDate.now());
            }

            UserResponseDto res = new UserResponseDto(reporter.getId(), reportedUser.getId(), req.getContent());
            return res;
        } else {
            // 이미 신고를 했다면 리턴
            throw new AlreadyReportException();
        }
    }

    void checkReport(Long userId){
        Member member = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Period period = Period.between(member.getLastReportedDay(), LocalDate.now());

        //&& period.getDays() <10
        if(member.isReported() == true && period.getDays() <10 ) {
            throw new ReportedUserException("신고된 유저 : 게시물 작성 권한 없음");
        }
        else if(member.isReported() == true && period.getDays() >=10 ){
            member.setReported(false);
            member.setLastReportedDay(null);
        }
    }

    // 신고 기간 풀렸을 시에 누적 신고 삭제 해줘야됨

}

