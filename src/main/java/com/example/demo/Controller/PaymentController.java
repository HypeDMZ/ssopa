package com.example.demo.Controller;


import com.example.demo.Service.MemberService;
import com.example.demo.Service.PaymentService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.payment.PaymentRequest;
import com.example.demo.entity.Member;
import com.example.demo.entity.Payment;
import com.example.demo.repository.MemberRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Api(tags = "PaymentController : 결제 관련 컨트롤러")
public class PaymentController {

    private final HttpResponseUtil httpResponseUtil;

    private final MemberRepository memberRepository;

    private final PaymentService paymentService;
    @Operation(summary = "결제 성공 handler", description = "결제 성공시 호출되는 handler")
    @GetMapping("/success")
    public ResponseEntity<?> getMeal(@RequestParam(value="paymentKey") String paymentKey, @RequestParam(value="orderId") String orderId, @RequestParam(value="amount") String amount) throws IOException, InterruptedException {

            String response= paymentService.CallAPI(paymentKey, orderId, amount);
            URI redirectUri = URI.create("https://localhost:3000/Post");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

    }

    @PostMapping("/request")
    public ResponseEntity<?> requestPayment(
            @Valid @RequestBody PaymentRequest request) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인이 필요합니다."));

        BigDecimal amount = new BigDecimal(request.getAmount());
        Payment payment = paymentService.requestPayment(member.getId(), request.getName(), amount,member.getPhonenumber());
        return ResponseEntity.ok(payment);
    }
}
