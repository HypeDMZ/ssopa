package com.example.demo.Controller;

import com.example.demo.Exception.Auth.alreadyRegisteredException;
import com.example.demo.Service.ApnsPushService;
import com.example.demo.Service.AuthService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.dto.auth.*;
import com.example.demo.dto.jwt.TokenReqDto;
import com.example.demo.dto.auth.MemberRequestDto;

import com.example.demo.entity.DeviceToken;
import com.example.demo.entity.PushPayload;
import com.example.demo.repository.DeviceTokenRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Payload;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "AuthController : 로그인/회원가입 관련 컨트롤러")
public class AuthController {
    private final AuthService authService;
    private final HttpResponseUtil httpResponseUtil;
    @Operation(summary = "디바이스토큰등록")
    @PostMapping("/registertoken")
    public ResponseEntity<?> signup(@RequestParam(value="deviceToken") String token) {
        try {
            return httpResponseUtil.createOKHttpResponse(authService.registerToken(token), "토큰 등록 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("토큰등록 실패: " + e.getMessage());
        }
    }

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
        try {
            return httpResponseUtil.createOKHttpResponse(authService.login(loginrequest), "로그인 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("로그인 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        try {
            return httpResponseUtil.createOKHttpResponse(authService.reissue(tokenRequestDto), "토큰 재발급 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("토큰 재발급 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "인증문자 보내기")
    @GetMapping("/check/sendSMS")
    public ResponseEntity<?> sendSMS(@RequestParam(value="to") String to){
        try {
            return httpResponseUtil.createOKHttpResponse(authService.PhoneNumberCheck(to,false), "인증문자 보내기 성공");
        }catch (alreadyRegisteredException e) {
            return httpResponseUtil.createBadRequestHttpResponse("이미 가입된 회원입니다.");
        }
        catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("인증문자 보내기 실패: " + e.getMessage());
        }
    }

    @GetMapping("/check/verifySMS")
    @Operation(summary = "인증문자 확인")
    public ResponseEntity<?> verifySMS(@RequestParam(value="to") String to,@RequestParam(value="code") String code){
        try {
            return httpResponseUtil.createOKHttpResponse(authService.verifySms(code,to), "인증문자 확인 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("인증문자 확인 실패: " + e.getMessage());
        }
    }

    @PostMapping("/findId")
    @Operation(summary = "아이디 찾기")
    public ResponseEntity<?> findID(@RequestBody FindIdDto dto) {
        try {
            return httpResponseUtil.createOKHttpResponse((authService.findID(dto.getName(), dto.getPhonenumber())), "아이디 찾기 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("아이디 찾기 실패: " + e.getMessage());
        }
    }

    @PostMapping("/findId/veritfySMS")
    @Operation(summary = "아이디 찾기 인증문자 확인")
    public ResponseEntity<?> findVerifyedID(@RequestBody FindVerifyedDto dto){
        try {
            return httpResponseUtil.createOKHttpResponse(authService.findIdverifySms(dto.getCode(),dto.getPhonenumber()), "아이디 찾기 인증문자 확인 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("아이디 찾기 인증문자 확인 실패: " + e.getMessage());
        }
    }

    @PostMapping("/resetpassword")
    @Operation(summary = "비밀번호 재설정")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto dto){
        try {
            return httpResponseUtil.createOKHttpResponse(authService.resetPassword(dto.getEmail(),dto.getPassword(), dto.getPasswordConfirm()), "비밀번호 재설정 성공");
        } catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("비밀번호 재설정 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "이메일 중복확인")
    @GetMapping("/check/duplicatedEmail")
    public ResponseEntity<?> checkDuplicatedEmail(@RequestParam(value="email") String email){
        try {
            return httpResponseUtil.createOKHttpResponse(authService.checkDuplicatedEmail(email), "인증문자 보내기 성공");
        }catch (alreadyRegisteredException e) {
            return httpResponseUtil.createBadRequestHttpResponse("이미 가입된 회원입니다.");
        }
        catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("이메일 중복 확인 실패" + e.getMessage());
        }
    }

    @Operation(summary = "휴대폰번호 중복확인")
    @GetMapping("/check/duplicatedNumber")
    public ResponseEntity<?> checkDuplicatedPhoneNumber(@RequestParam(value="phonenumber") String phonenumber){
        try {
            return httpResponseUtil.createOKHttpResponse(authService.checkDuplicatedNumber(phonenumber), "인증문자 보내기 성공");
        }catch (alreadyRegisteredException e) {
            return httpResponseUtil.createBadRequestHttpResponse("이미 가입된 회원입니다.");
        }
        catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("휴대폰번호 중복 확인 실패" + e.getMessage());
        }
    }

    @Operation(summary = "닉네임 조회")
    @GetMapping("/get/nickname")
    public ResponseEntity<?> getnickname(@RequestParam(value="email") String email){
        try {
            return httpResponseUtil.createOKHttpResponse(authService.getNickname(email), "인증문자 보내기 성공");
        }
        catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("닉네임 조회 실패" + e.getMessage());
        }
    }


}
