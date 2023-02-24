package com.example.demo.Controller;


import com.example.demo.Service.ReportService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.dto.Report.UserReportRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
@Api(tags = "ReportController : 신고 관련 컨트롤러")
public class ReportController {

    private final ReportService reportService;

    private final HttpResponseUtil httpResponseUtil;

    @ApiOperation(value = "유저 신고", notes = "유저를 신고합니다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/users")
    public ResponseEntity<?> reportUser (@RequestBody UserReportRequest userReportRequest) {
        try {
            return httpResponseUtil.createOKHttpResponse(reportService.reportUser(userReportRequest), "유저 신고 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("유저 신고 실패: " + e.getMessage());
            /*
            * 유저 신고 실패:
            *  Null return value from advice does not match primitive return type for:
            * public abstract boolean com.example.demo.repository.MemberReportRepository.findByReporterIdAndReportedUserId(java.lang.Long,java.lang.Long)
            * */
        }
    }
}

