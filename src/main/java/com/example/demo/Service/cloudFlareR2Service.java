package com.example.demo.Service;


//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

//@Service
//@RequiredArgsConstructor
public class cloudFlareR2Service {

//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private final AmazonS3 amazonS3;
//
//    public String uploadImage(MultipartFile multipartFile) {
//
//
//        String fileName = multipartFile.getOriginalFilename();
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(multipartFile.getSize());
//        objectMetadata.setContentType(multipartFile.getContentType());
//
//        try(InputStream inputStream = multipartFile.getInputStream()) {
//            amazonS3.putObject(new PutObjectRequest(bucket, createFileName(fileName), inputStream, new ObjectMetadata())
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//        } catch(IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
//        }
//         return fileName;
//    }
//
//
//
//    public void deleteImage(String fileName) {
//        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
//    }
//
//    private String createFileName(String fileName) {
//        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
//    }
//
//    private String getFileExtension(String fileName) {
//        try {
//            return fileName.substring(fileName.lastIndexOf("."));
//        } catch (StringIndexOutOfBoundsException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
//        }
//    }
}
