package com.example.demo.Service;

import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.auth.syncTokenResponseDto;
import com.example.demo.dto.member.MemberResponseDto;
import com.example.demo.entity.DeviceToken;
import com.example.demo.entity.Member;
import com.example.demo.repository.DeviceTokenRepository;
import com.example.demo.repository.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final NicknameGenerator nicknameGenerator;
    private final DeviceTokenRepository deviceTokenRepository;

    public MemberResponseDto getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    @Transactional
    public MemberResponseDto changeMemberNickname() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        member.setNickname(nicknameGenerator.generateRandomNickname());
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto changeMemberPassword(String email, String exPassword, String newPassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.setPassword(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));
    }


    //apns 토큰 등록
    public syncTokenResponseDto syncToken(String token) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));


        // 멤버ID가 이미 등록이 된 동일한 토큰이 있는지 확인
        Optional<DeviceToken> deviceToken = deviceTokenRepository.findByTokenAndMemberIdIsNotNull(token);

        //이미 있으면
        if (deviceToken.isPresent()) {
            DeviceToken temp = deviceToken.get();
            temp.setMember(member);
            deviceTokenRepository.save(temp);
            return syncTokenResponseDto.builder()
                    .success(true)
                    .build();
        //아니라면
        } else {
            //멤버ID가 없는 동일한 토큰이 있는지 확인
            deviceTokenRepository.findAllByMemberIdIsNullAndTokenEquals(token).forEach(Token -> {
                Token.setMember(member);
                Token.setIsRegistered(true);

            });

            return syncTokenResponseDto.builder()
                    .success(true)
                    .build();
        }

    }
}