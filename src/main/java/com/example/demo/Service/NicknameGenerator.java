package com.example.demo.Service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@Component
public class NicknameGenerator {
    private static final String ADJECTIVE_FILE_PATH = "classpath:adjective.txt";
    private static final String NOUN_FILE_PATH = "classpath:noun.txt";
    private final List<String> adjectives;
    private final List<String> nouns;

    public NicknameGenerator() throws IOException {
        // 형용사 목록을 로드
        File adjectiveFile = ResourceUtils.getFile(ADJECTIVE_FILE_PATH);
        adjectives = Files.readAllLines(adjectiveFile.toPath());

        // 명사 목록을 로드
        File nounFile = ResourceUtils.getFile(NOUN_FILE_PATH);
        nouns = Files.readAllLines(nounFile.toPath());
    }

    public String generateRandomNickname() {
        Random random = new Random();
        int adjectiveIndex = random.nextInt(adjectives.size());
        int nounIndex = random.nextInt(nouns.size());
        String adjective = StringUtils.capitalize(adjectives.get(adjectiveIndex));
        String noun = StringUtils.capitalize(nouns.get(nounIndex));
        return adjective + " " + noun;
    }
}
