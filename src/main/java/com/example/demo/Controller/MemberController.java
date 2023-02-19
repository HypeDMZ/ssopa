package com.example.demo.Controller;

import com.example.demo.Service.MemberService;
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

    @GetMapping("/me")
    @ApiOperation(value = "내 정보 조회")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getNickname());
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    @PostMapping("/nickname")
    @ApiOperation(value = "닉네임 변경 요청")
    public ResponseEntity<MemberResponseDto> setMemberNickname(@RequestBody ChangeNicknameRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberNickname(request.getEmail(), request.getNickname()));
    }

    @ApiOperation(value = "비밀번호 변경 요청")
    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getEmail(),request.getExPassword(), request.getNewPassword()));
    }

}