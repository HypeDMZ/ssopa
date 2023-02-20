package com.example.demo.Controller;

import com.example.demo.Service.MemberService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.dto.member.ChangeNicknameRequestDto;
import com.example.demo.dto.member.ChangePasswordRequestDto;
import com.example.demo.dto.member.MemberResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Api(tags = "MemberController : 회원 정보 요청 관련 컨트롤러")
public class MemberController {
    private final MemberService memberService;
    private final HttpResponseUtil httpResponseUtil;

    @GetMapping("/me")
    @ApiOperation(value = "내 정보 조회")
    public ResponseEntity<?> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        try {
            return httpResponseUtil.createOKHttpResponse(myInfoBySecurity, "내 정보 조회 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("내 정보 조회 실패: " + e.getMessage());
        }
    }

    @PostMapping("/nickname")
    @ApiOperation(value = "닉네임 변경 요청")
    public ResponseEntity<?> setMemberNickname(@RequestBody ChangeNicknameRequestDto request) {
        // TODO : 닉네임 기능 추가 후 구현
        try {
            return httpResponseUtil.createOKHttpResponse(memberService.changeMemberNickname(request.getEmail(), request.getNickname()), "닉네임 변경 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("닉네임 변경 실패: " + e.getMessage());
        }
    }

    @ApiOperation(value = "비밀번호 변경 요청")
    @PostMapping("/password")
    public ResponseEntity<?> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        try {
            return httpResponseUtil.createOKHttpResponse(memberService.changeMemberPassword(request.getEmail(), request.getExPassword(), request.getNewPassword()), "비밀번호 변경 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("비밀번호 변경 실패: " + e.getMessage());
        }
    }
}