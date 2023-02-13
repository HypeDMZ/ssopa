package com.example.demo.Controller;

import com.example.demo.Service.AuthService;
import com.example.demo.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "AuthController : 로그인/회원가입 관련 컨트롤러")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입")
    @ApiResponse(code = 200, message = "회원가입 성공")
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }
    @Operation(summary = "로그인")
    @ApiResponse(code = 200, message = "로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginrequest) {
        return ResponseEntity.ok(authService.login(loginrequest));
    }
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/check/sendSMS")
    public @ResponseBody ResponseEntity<SmsDto> sendSMS(@RequestParam(value="to") String to){
        return ResponseEntity.ok(authService.PhoneNumberCheck(to));
    }

    @GetMapping("/check/verifySMS")
    public @ResponseBody ResponseEntity<SmsDto> verifySMS(@RequestParam(value="to") String to,@RequestParam(value="code") String code){
        return ResponseEntity.ok(authService.verifySms(code,to));
    }
}