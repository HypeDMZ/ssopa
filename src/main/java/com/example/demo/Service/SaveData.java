package com.example.demo.Service;

import com.example.demo.entity.Hot;
import com.example.demo.repository.HotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SaveData {
    private final HotRepository hotRepository;
    public void saveData(Hot hot) {
        long count = hotRepository.count();
        if (hot.getWeight() == 5){
            // hot.postId랑, hot.userid가 데이터베이스에 유무를 확인후 있으면 막음
            Optional<Hot> existingHot = hotRepository.findByPostIdAndUserId(hot.getPostId(), hot.getUserId());
            if (existingHot.isPresent()) {
                throw new RuntimeException("Hot already exists for postId=" + hot.getPostId() + " and userId=" + hot.getUserId());
            }
            // 데이터베이스에 없으면 저장
        }
        if (count < 300) {
            // Add new record with auto-generated id
            hotRepository.save(hot);
        } else {
            // Update existing record with id 1
            Hot hotRecord = hotRepository.findById(1L).orElseThrow(() -> new RuntimeException("Record not found"));
            hotRecord.setPostId(hot.getPostId());
            hotRecord.setWeight(hot.getWeight());
            hotRecord.setUserId(hot.getUserId());
            hotRepository.save(hotRecord);
        }
    }
}
