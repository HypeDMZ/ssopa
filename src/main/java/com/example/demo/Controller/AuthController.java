package com.example.demo.Controller;

import com.example.demo.Service.AuthService;
import com.example.demo.dto.auth.FindIdResponseDto;
import com.example.demo.dto.auth.LoginDto;
import com.example.demo.dto.auth.SmsDto;
import com.example.demo.dto.jwt.TokenDto;
import com.example.demo.dto.jwt.TokenReqDto;
import com.example.demo.dto.member.MemberRequestDto;
import com.example.demo.dto.member.MemberResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indicates that this class defines a RESTful controller
@RequestMapping("/auth") // Maps HTTP requests to specific handler methods
@RequiredArgsConstructor // Generates a constructor with required fields
@CrossOrigin(origins = "http://localhost:3000")
@Api(tags = "AuthController : 로그인/회원가입 관련 컨트롤러") // Provides metadata for the Swagger documentation
public class AuthController {
    private final AuthService authService; // Injects the AuthService dependency into the constructor

    @Operation(summary = "회원가입") // Provides metadata for the Swagger documentation
    @ApiResponse(code = 200, message = "회원가입 성공") // Provides metadata for the Swagger documentation
    @PostMapping("/signup") // Maps HTTP POST requests for the "/signup" endpoint to the signup() method
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto)); // Returns an HTTP OK response with the result of the signup() method
    }

    @Operation(summary = "로그인") // Provides metadata for the Swagger documentation
    @ApiResponse(code = 200, message = "로그인 성공") // Provides metadata for the Swagger documentation
    @PostMapping("/login") // Maps HTTP POST requests for the "/login" endpoint to the login() method
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginrequest) {
        return ResponseEntity.ok(authService.login(loginrequest)); // Returns an HTTP OK response with the result of the login() method
    }

    @PostMapping("/reissue") // Maps HTTP POST requests for the "/reissue" endpoint to the reissue() method
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto)); // Returns an HTTP OK response with the result of the reissue() method
    }

    @GetMapping("/check/sendSMS") // Maps HTTP GET requests for the "/check/sendSMS" endpoint to the sendSMS() method
    public @ResponseBody ResponseEntity<SmsDto> sendSMS(@RequestParam(value="to") String to){
        return ResponseEntity.ok(authService.PhoneNumberCheck(to)); // Returns an HTTP OK response with the result of the PhoneNumberCheck() method
    }

    @GetMapping("/check/verifySMS") // Maps HTTP GET requests for the "/check/verifySMS" endpoint to the verifySMS() method
    public @ResponseBody ResponseEntity<SmsDto> verifySMS(@RequestParam(value="to") String to,@RequestParam(value="code") String code){
        return ResponseEntity.ok(authService.verifySms(code,to)); // Returns an HTTP OK response with the result of the verifySms() method
    }

    @GetMapping("/findId") // Maps HTTP GET requests for the "/findId" endpoint to the findID() method
    @ApiOperation(value = "아이디 찾기") // Provides metadata for the Swagger documentation
    public @ResponseBody ResponseEntity<Boolean> findID(@RequestParam(value="to") String phonenumber) {
        return ResponseEntity.ok((authService.findID(phonenumber))); // Returns an HTTP OK response with the result of the findID() method
    }

    @GetMapping("/findId/veritfySMS") // Maps HTTP GET requests for the "/findId/veritfySMS" endpoint to the findID() method
    public @ResponseBody ResponseEntity<FindIdResponseDto> findID(@RequestParam(value="to") String to, @RequestParam(value="code") String code){
        return ResponseEntity.ok(authService.findIdverifySms(code,to)); // Returns an HTTP OK response with the result of the findIdverifySms() method
    }
}
