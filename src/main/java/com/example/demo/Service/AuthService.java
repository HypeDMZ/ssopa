package com.example.demo.Service;

import com.example.demo.dto.auth.FindIdResponseDto;
import com.example.demo.dto.auth.LoginDto;
import com.example.demo.dto.auth.SmsDto;
import com.example.demo.dto.auth.SuccessDto;
import com.example.demo.dto.jwt.TokenDto;
import com.example.demo.dto.jwt.TokenReqDto;
import com.example.demo.dto.member.MemberRequestDto;
import com.example.demo.dto.member.MemberResponseDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.RefreshToken;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.VerifySmsRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Configuration
@Component
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsCertificationDao;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final VerifySmsRepository verifySmsRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private String apiKey = "NCSWUXEY6GVEX4US";
    private String apiSecret = "OAEENSHT7XUHYHJLPHUIAWVWVJSE3XC7";
    private final DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateKeyException("이미 가입되어 있는 유저입니다");
        }

        Member member = requestDto.toMember(passwordEncoder);
        if(smsCertificationDao.hasKey(member.getPhonenumber())) {
            // 번호인증이 완료됐는지 확인하는 로직
            String Origincert = smsCertificationDao.getSmsCertification(member.getPhonenumber());
            String[] split = Origincert.split(":");
            if(split[1].equals("01")){
                smsCertificationDao.removeSmsCertification(member.getPhonenumber());
                return MemberResponseDto.of(memberRepository.save(member));
            }
        }else{
            throw new RuntimeException("비정상접근입니다");
        }
        return MemberResponseDto.of(null);
    }

    public TokenDto login(LoginDto loginrequest) {
        UsernamePasswordAuthenticationToken authenticationToken = loginrequest.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .email(loginrequest.getEmail())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenReqDto tokenRequestDto) {
        /*
         *  accessToken 은 JWT Filter 에서 검증되고 옴
         * */
        String originAccessToken = tokenRequestDto.getAccessToken();
        String originRefreshToken = tokenRequestDto.getRefreshToken();

        // refreshToken 검증
        Boolean refreshTokenFlag = tokenProvider.validateRefreshToken(originRefreshToken);
        if (refreshTokenFlag == false) {
            throw new RuntimeException("refreshToken 이 만료되었습니다");
        }

        // 2. Access Token 에서 Member Email 가져오기
        Authentication authentication = tokenProvider.getAuthentication(originAccessToken);
        Member authMember = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));

        // 3. 저장소에서 Member Email 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(authMember.getEmail())
                .orElseThrow(() -> new RuntimeException("로그아웃된 사용자 입니다"));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(originRefreshToken)) {
            throw new RuntimeException("refreshToken 이 일치하지 않습니다");
        }

        Authentication newAuthentication = tokenProvider.getAuthentication(originAccessToken);
        TokenDto tokenDto= tokenProvider.generateTokenDto(newAuthentication);

        // 6. 저장소 정보 업데이트 (dirtyChecking으로 업데이트)
        refreshToken.updateValue(tokenDto.getRefreshToken());

        // 토큰 발급
        return tokenDto;
    }

    public SmsDto PhoneNumberCheck(String phoneNumber) {
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }
        Message coolsms = new Message();
        coolsms.setFrom("01046306320");
        coolsms.setTo(phoneNumber);
        coolsms.setText("[ssopa]인증번호는 [" + numStr + "] 입니다.");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(coolsms));
        System.out.println(response);
        smsCertificationDao.createSmsCertification(phoneNumber,numStr+":0"); //저장

        return new SmsDto().builder()
                .success(true)
                .build();
    }

    //사용자가 입력한 인증번호가 Redis에 저장된 인증번호와 동일한지 확인
    public SmsDto verifySms(String certNumber,String phoneNumber) {
        if (isVerify(certNumber+":0",phoneNumber)) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }else{
            System.out.println("인증번호 일치");
            smsCertificationDao.check_as_verfied(phoneNumber);
        }
        return new SmsDto().builder()
                .success(true)
                .build();
    }

    private boolean isVerify(String certNumber,String phoneNumber) {
        return !(smsCertificationDao.hasKey(phoneNumber) &&
                smsCertificationDao.getSmsCertification(phoneNumber)
                        .equals(certNumber));
    }

    public boolean findID(String name, String phonenumber) {
        if(memberRepository.findByNameAndPhonenumber(name,phonenumber) != null) {
            PhoneNumberCheck(phonenumber);
            return true;
        }else{
            return false;
        }
    }

    public FindIdResponseDto findIdverifySms(String certNumber, String phoneNumber) {
        if (isVerify(certNumber+":0",phoneNumber)) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }else{
            System.out.println("인증번호 일치");
            smsCertificationDao.removeSmsCertification(phoneNumber);

            Member member = memberRepository.findByPhonenumber(phoneNumber).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
            System.out.println("로그인 정보 : "+member.getEmail());

            return new FindIdResponseDto().builder()
                    .email(member.getEmail())
                    .build();
        }
    }
    public SuccessDto resetPassword(String email, String password, String passwordConfirm) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));

        if(!password.equals(passwordConfirm)){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
        return new SuccessDto().builder()
                .success(true)
                .build();
    }
}