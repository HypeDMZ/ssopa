package com.example.demo.Service;


import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class NeisService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${neis.apiKey}")
    private String apiKey;

    private static final String type = "json";

    private static final String pIndex = "1";
    private static final String pSize = "100";

    public ResponseEntity<String> getMeal(String atptOfcdcScCode, String sdSchulCode, String mlsvYmd) throws IOException {
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String apiUrl = "https://open.neis.go.kr/hub/mealServiceDietInfo?KEY="+apiKey+"&Type="+type+"&pIndex="+pIndex+"&pSize="+pSize+"&ATPT_OFCDC_SC_CODE="+atptOfcdcScCode+"&SD_SCHUL_CODE="+sdSchulCode+"&MLSV_YMD="+mlsvYmd;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class, 25);
        return responseEntity;
    }


    }

