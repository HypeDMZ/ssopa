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
        if (hot.getWeight() == 5) {
            Optional<Hot> existingHot = hotRepository.findByPostIdAndUserId(hot.getPost().getId(), hot.getUserId());
            if (existingHot.isPresent()) {
                throw new RuntimeException("Hot already exists for postId=" + hot.getPost().getId() + " and userId=" + hot.getUserId());
            }
        }

        if (count < 300) {
            // Add new record with auto-generated id
            hotRepository.save(hot);
        } else {
            // Update existing record with smallest id value
            Hot hotRecord = hotRepository.findFirstByOrderByIdAsc();
            hotRecord.setPost(hot.getPost());
            hotRecord.setWeight(hot.getWeight());
            hotRecord.setUserId(hot.getUserId());
            hotRepository.save(hotRecord);
        }
    }
}

