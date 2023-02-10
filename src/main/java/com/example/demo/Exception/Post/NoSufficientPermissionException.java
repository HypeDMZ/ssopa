package com.example.demo.Exception.Post;

//게시글  CRUD 관련 예외처리 클래스
public class NoSufficientPermissionException extends RuntimeException {
    public NoSufficientPermissionException() {
    }
    public  NoSufficientPermissionException(String message) {
        super(message);
    }
}

