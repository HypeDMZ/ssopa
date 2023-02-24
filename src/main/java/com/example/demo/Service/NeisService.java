package com.example.demo.Service;


import com.google.gson.Gson;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

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

    public ResponseEntity<String> getMeal(String atptOfcdcScCode, String sdSchulCode, String mlsvYmd) throws IOException, java.io.IOException {
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String apiUrl = "https://open.neis.go.kr/hub/mealServiceDietInfo?KEY="+apiKey+"&Type="+type+"&pIndex="+pIndex+"&pSize="+pSize+"&ATPT_OFCDC_SC_CODE="+atptOfcdcScCode+"&SD_SCHUL_CODE="+sdSchulCode+"&MLSV_YMD="+mlsvYmd;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class, 25);

        Gson gson = new Gson();

        // Json 문자열 -> Map
        Map<String, Object> map = gson.fromJson(responseEntity.getBody(), Map.class);


        map.put("update_time", LocalDateTime.now().toString());

        // Map 출력
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }


        // Write the Java Map to a JSON file using Gson
        FileWriter writer = new FileWriter("src/main/resources/meal.json");
        gson.toJson(map, writer);
        writer.close();


        return responseEntity;
    }


    }

