package com.example.demo.Service;

import com.eatthepath.pushy.apns.PushType;
import com.example.demo.Exception.Post.NoSufficientPermissionException;
import com.example.demo.Exception.Report.ReportedUserException;
import com.example.demo.config.SecurityUtil;
import com.example.demo.dto.post.*;
import com.example.demo.entity.*;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final HeartRepository heartRepository;
    private final HotRepository hotRepository;
    private final ReportService reportService;
    private final ApnsPushService apnsPushService;
    private final DeviceTokenRepository deviceTokenRepository;
    private final SaveData saveData;

    @Transactional
    public PostResponseDto newpost(String title, String content, String category) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());

        reportService.checkReport(member.getId()); // 신고당한 유저는 게시글 작성 제한

        Post post = Post.builder()
                .title(title)
                .writer(member.getNickname())
                .content(content)
                .category(postCategory.valueOf(category))
                .created_date(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .deleteYn(Boolean.FALSE)
                .noticeYn(Boolean.FALSE)
                .view_cnt(0)
                .like_cnt(0)
                .userId(member.getId())
                .build();
        List<DeviceToken> deviceTokens = deviceTokenRepository.findAllByMemberIdIsNotNull();
        List<String> tokens = new ArrayList<>();
        for(DeviceToken deviceToken : deviceTokens) {
            tokens.add(deviceToken.getToken());
        }
        apnsPushService.sendPush(tokens, PushPayload.builder().alertBody(post.getTitle()).alertTitle("새로운 게시글").sound("bingbong.aiff").build());



        return PostResponseDto.of(postRepository.save(post));
    }

    @Transactional
    public PostReadDto readpost (Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
        post.setView_cnt(post.getView_cnt()+1);
        Boolean isLiked = false;
        if(heartRepository.existsHeartByPostIdAndUserId(id, member.getId())) {
            isLiked = true;
        }
        // hot테이블에 저장
        Hot hot = Hot.builder()
                .post(post)
                .userId(member.getId())
                .weight(1)
                .build();
        saveData.saveData(hot);
        return PostReadDto.of(postRepository.save(post),isLiked);
    }

    @Transactional
    public PostDeleteDto deletepost (Long id) throws RuntimeException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
        // post 데이터베이스 지우기
        if (post.getUserId() == member.getId()) {
            postRepository.delete(post);
            return PostDeleteDto.of(post);
        } else {
            throw new NoSufficientPermissionException();
        }
    }

    @Transactional
    public PostUpdateDto updatepost(String title, String content, Long id)  {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
        // 저자 일치 확인 -> 아니면 error

        if (post.getUserId() == member.getId()) {
            post.updateValue(title, content,LocalDateTime.now());
            return PostUpdateDto.of(post);
        } else {
            throw new NoSufficientPermissionException();
        }
    }

    @Transactional
    public List<LoadDto> loadpost (String category, int page) {
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
//        System.out.println("로그인 정보 : "+member.getEmail());

        Page<LoadDto> loadDtoList;
        if (postRepository.existsPostByCategory(postCategory.valueOf(category))) {
            PageRequest pageRequest = PageRequest.of(page, 20, Sort.by("modifiedDate").descending());
            loadDtoList = postRepository.findByCategory(postCategory.valueOf(category), pageRequest);

        }
        else {
            throw new NoSufficientPermissionException();
        }
        return loadDtoList.getContent();
    }

    @Transactional
    public HeartDto heartpost(Long post_id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if(heartRepository.existsHeartByPostIdAndUserId(post_id, member.getId())){
            Heart heart = heartRepository.findByPostIdAndUserId(post_id, member.getId());
            // like_cnt -1
            Post post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
            post.setLike_cnt(post.getLike_cnt()-1);
            postRepository.save(post);
            heartRepository.delete(heart);
            return HeartDto.of(heart);
        }
        else{
            Post post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));

            if(hotRepository.existsByPostIdAndUserId(post.getId(), member.getId())){

            }
            else{
                Hot hot = Hot.builder()
                        .post(post)
                        .userId(member.getId())
                        .weight(5)
                        .build();
                saveData.saveData(hot);
            }
            Heart heart = Heart.builder()
                    .post(post)
                    .userId(member.getId())
                    .build();

            // like_cnt +1
            post.setLike_cnt(post.getLike_cnt()+1);
            postRepository.save(post);
            heartRepository.save(heart);



            PushPayload payload = PushPayload.builder().alertBody(post.getTitle()).alertTitle("새로운 좋아요").sound("bingbong.aiff").build();
            List<Member> members = new ArrayList<>();
            Member postOwner = memberRepository.findById(post.getUserId()).orElseThrow(() -> new RuntimeException("게시글 작성자 정보가 없습니다"));
            members.add(postOwner);
            apnsPushService.sendPushByMember(members,payload);


            return HeartDto.of(heart);
        }
    }

    @Transactional
    public List<LoadDto> myWritePost () {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        System.out.println("로그인 정보 : "+member.getEmail());

        List<LoadDto> loadDtoList;
        if (postRepository.findAllByUserId(member.getId()) != null) {
            loadDtoList = postRepository.findAllByUserId(member.getId());
        }
        else {
            throw new NoSufficientPermissionException();
        }
        return loadDtoList;
    }

    public List<LoadDto> getHotList() {
        List<Hot> getList = hotRepository.findAll(Sort.by(Sort.Direction.DESC, "weight"));
        Map<Post, HotDto> hotMap = new LinkedHashMap<>();
        for (Hot hot : getList) {
            Post post = hot.getPost();
            HotDto hotDTO = hotMap.get(post);
            if (hotDTO == null) {
                hotMap.put(post, new HotDto(hot));
            } else {
                hotDTO.setWeight(hotDTO.getWeight() + hot.getWeight());
            }
        }
        List<HotDto> hotList = new ArrayList<>(hotMap.values());
        hotList.sort(Comparator.comparing(HotDto::getWeight).reversed());
        // hotList로 LoadDto를 만들어서 반환
        List<LoadDto> loadDtoList = new ArrayList<>();
        for (HotDto hotDto : hotList) {
            Post post = postRepository.findById(hotDto.getPost().getId()).orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다"));
            loadDtoList.add(LoadDto.of(post));
        }
        return loadDtoList;
    }
}
