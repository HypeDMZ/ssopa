package com.example.demo.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.OK;

@Component
public class HttpResponseUtil {
    public ResponseEntity<?> createOKHttpResponse(Object object, String responseMessage) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        message.setStatus(Message.StatusEnum.OK);
        message.setMessage(responseMessage);
        message.setData(object);
        return new ResponseEntity<>(message, headers, OK);
    }
}
