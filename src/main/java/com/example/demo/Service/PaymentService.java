package com.example.demo.Service;


import com.example.demo.dto.member.MemberResponseDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Payment;
import com.example.demo.entity.PaymentStatus;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Objects;

import static lombok.Lombok.checkNotNull;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {


        @Value("${toss.secret}")
        private String secretKey;

        private String base64SecretKey;

        private final NicknameGenerator nicknameGenerator;

        private final PaymentRepository paymentRepository;

        private final MemberRepository memberRepository;

        @PostConstruct
        public void init() {
            base64SecretKey = java.util.Base64.getEncoder().encodeToString((secretKey+":").getBytes());
        }



        @Transactional
        public String CallAPI(String paymentKey, String orderId, String amount) throws IOException, InterruptedException {

            Payment payment;
            try{
                payment = paymentRepository.findByOrderId(orderId).get();
            }catch (NullPointerException e) {
                return "Not Found";
            }catch(Exception e){
                return "Error";
            }


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                    .header("Authorization", "Basic "+base64SecretKey)
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"paymentKey\":\""+paymentKey+"\",\"amount\":"+amount+",\"orderId\":\""+orderId+"\"}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                System.out.println(response.body());
                CheckTransactionAsPaid(payment, orderId);


                Member member = memberRepository.findById(payment.getUserId()).get();
                member.setNickname(nicknameGenerator.generateRandomNickname());
                memberRepository.save(member);
                return response.headers().toString();

            }

            return response.headers().toString();
        }


    @Transactional
    public Payment CheckTransactionAsPaid(Payment payment, String orderId) {
        checkNotNull(payment, "payment must be provided.");
        checkNotNull(orderId, "buyer must be provided.");


        if (payment.getStatus().equals(PaymentStatus.READY)) {
            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);
        } else {
            throw new IllegalStateException("이미 결제가 완료된 주문입니다.");
        }


        return payment;

    }


    @Transactional
    public Payment requestPayment(Long member_id, String name, BigDecimal amount,String number) {
        Payment payment = new Payment();
        payment.setUserId(member_id);
        payment.setOrderId(String.valueOf(Objects.hash(member_id, name, amount, System.currentTimeMillis(),number)));
        payment.setName(name);
        payment.setAmount(amount);
        payment.setCreateAt(LocalDateTime.now());
        payment.setTransactionKey(String.valueOf(Objects.hash(member_id, name, amount, System.currentTimeMillis(),number)));
        return paymentRepository.save(payment);
    }
}
