package com.example.demo.Service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
@Component
public class NicknameGenerator {
    private static final String ADJECTIVE_FILE_PATH = "/adjective.txt";
    private static final String NOUN_FILE_PATH = "/noun.txt";
    private final List<String> adjectives;
    private final List<String> nouns;

    public NicknameGenerator() throws IOException {

        try (InputStream inputStream = getClass().getResourceAsStream(ADJECTIVE_FILE_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            adjectives = reader.lines().collect(Collectors.toList());
        }

        try (InputStream inputStream = getClass().getResourceAsStream(NOUN_FILE_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            nouns = reader.lines().collect(Collectors.toList());
        }

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
