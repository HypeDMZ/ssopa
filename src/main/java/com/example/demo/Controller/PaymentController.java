package com.example.demo.Controller;


import com.example.demo.Service.PaymentService;
import com.example.demo.common.HttpResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Api(tags = "PaymentController : 결제 관련 컨트롤러")
public class PaymentController {

    private final HttpResponseUtil httpResponseUtil;

    private final PaymentService paymentService;
    @Operation(summary = "급식 정보 가져오기")
    @GetMapping("/success")
    public ResponseEntity<?> getMeal(@RequestParam(value="paymentKey") String paymentKey, @RequestParam(value="orderId") String orderId, @RequestParam(value="amount") String amount) throws IOException, InterruptedException {


            String response= paymentService.CallAPI(paymentKey, orderId, amount);

            return httpResponseUtil.createOKHttpResponse(response,"메세지");

    }
}
