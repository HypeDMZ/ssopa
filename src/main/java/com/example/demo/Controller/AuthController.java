package com.example.demo.Controller;

import com.example.demo.Service.AuthService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.dto.auth.*;
import com.example.demo.dto.jwt.TokenDto;
import com.example.demo.dto.jwt.TokenReqDto;
import com.example.demo.dto.member.MemberRequestDto;
import com.example.demo.dto.member.MemberResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "AuthController : 로그인/회원가입 관련 컨트롤러")
public class AuthController {
    private final AuthService authService;
    private final HttpResponseUtil httpResponseUtil;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto requestDto) {
        try {
            return httpResponseUtil.createOKHttpResponse(authService.signup(requestDto), "회원가입 성공");
        } catch (DuplicateKeyException e) {
            return httpResponseUtil.createBadRequestHttpResponse("이미 존재하는 데이터로 회원가입할 수 없습니다.");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("회원가입 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginrequest) {
        return httpResponseUtil.createOKHttpResponse(authService.login(loginrequest), "로그인 성공");
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        return httpResponseUtil.createOKHttpResponse(authService.reissue(tokenRequestDto), "토큰 재발급 성공");
    }

    @Operation(summary = "인증문자 보내기")
    @GetMapping("/check/sendSMS")
    public ResponseEntity<?> sendSMS(@RequestParam(value="to") String to){
        return httpResponseUtil.createOKHttpResponse(authService.PhoneNumberCheck(to), "인증번호 전송 성공");
    }

    @GetMapping("/check/verifySMS")
    @Operation(summary = "인증문자 확인")
    public ResponseEntity<?> verifySMS(@RequestParam(value="to") String to,@RequestParam(value="code") String code){
        return httpResponseUtil.createOKHttpResponse(authService.verifySms(code,to), "인증번호 확인 성공");
    }

    @PostMapping("/findId")
    @Operation(summary = "아이디 찾기")
    public ResponseEntity<?> findID(@RequestBody FindIdDto dto) {
        return httpResponseUtil.createOKHttpResponse((authService.findID(dto.getName(), dto.getPhonenumber())), "아이디 찾기 성공");
    }

    @PostMapping("/findId/veritfySMS")
    @Operation(summary = "아이디 찾기 인증문자 확인")
    public ResponseEntity<?> findVerifyedID(@RequestBody FindVerifyedDto dto){
        return httpResponseUtil.createOKHttpResponse(authService.findIdverifySms(dto.getCode(),dto.getPhonenumber()), "인증번호 확인 성공");
    }

    @PostMapping("/resetpassword")
    @Operation(summary = "비밀번호 재설정")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto dto){
        return httpResponseUtil.createOKHttpResponse(authService.resetPassword(dto.getEmail(),dto.getPassword(), dto.getPasswordConfirm()), "비밀번호 재설정 성공");
    }
}
