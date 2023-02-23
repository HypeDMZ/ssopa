package com.example.demo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.OK;

@Component
public class HttpResponseUtil {
    public ResponseEntity<?> createOKHttpResponse(Object data, String message) {
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.OK.value(), message, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> createBadRequestHttpResponse(String message) {
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> createInternalServerErrorHttpResponse(String message) {
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    private static class ApiResponse<T> {
        private int status;
        private String message;
        private T data;
        public ApiResponse(int status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }
    }
}
