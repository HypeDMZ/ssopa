package com.example.demo.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class PaymentService {


        @Value("${toss.secret}")
        private String secretKey;

        private String base64SecretKey;

        @PostConstruct
        public void init() {
            base64SecretKey = java.util.Base64.getEncoder().encodeToString((secretKey+":").getBytes());
        }



        public String CallAPI(String paymentKey, String orderId, String amount) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                    .header("Authorization", "Basic "+base64SecretKey)
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"paymentKey\":\""+paymentKey+"\",\"amount\":"+amount+",\"orderId\":\""+orderId+"\"}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response.headers().toString();
        }
}
