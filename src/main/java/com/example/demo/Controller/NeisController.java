package com.example.demo.Controller;

import com.example.demo.Exception.Auth.alreadyRegisteredException;
import com.example.demo.Service.NeisService;
import com.example.demo.common.HttpResponseUtil;
import com.example.demo.config.SecurityUtil;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/neis")
@RequiredArgsConstructor
@Api(tags = "NeisController : 나이스 관련 컨트롤러")
public class NeisController {
    private final HttpResponseUtil httpResponseUtil;

    private final MemberRepository memberRepository;

    private final NeisService neisService;

    @Operation(summary = "급식 정보 가져오기")
    @GetMapping("/meal")
    public ResponseEntity<?> getMeal(@RequestParam(value="orgcode") String ATPT_OFCDC_SC_CODE, @RequestParam(value="sccode") String SD_SCHUL_CODE, @RequestParam(value="day") String MLSV_YMD){

        try {
            ResponseEntity<String> response= neisService.getMeal(ATPT_OFCDC_SC_CODE, SD_SCHUL_CODE, MLSV_YMD);

            return httpResponseUtil.createOKHttpResponse(response,"메세지");
        }
        catch (Exception e) {
            return httpResponseUtil.createInternalServerErrorHttpResponse("급식 정보 확인 실패: " + e.getMessage());
        }
    }
}
