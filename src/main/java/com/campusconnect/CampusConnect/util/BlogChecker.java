package com.campusconnect.CampusConnect.util;

import com.campusconnect.CampusConnect.dto.PostDTO;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class BlogChecker {

    private static final Set<String> ILLEGAL_WORDS = new HashSet<>();

    static {
        loadIllegalWords();
    }

    private static void loadIllegalWords() {
        try (InputStream inputStream = BlogChecker.class.getClassLoader().getResourceAsStream("unwantedWords.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            ILLEGAL_WORDS.addAll(reader.lines()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet()));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading bad words: " + e.getMessage());
        }
    }

    // Regular expression to remove non-alphabetic characters
    private static final Pattern CLEAN_PATTERN = Pattern.compile("[^a-zA-Z\\s]");

    public static boolean checkTheContent(PostDTO postDTO) {
        if (postDTO == null || isEmpty(postDTO.getTitle()) || isEmpty(postDTO.getContent())) {
            return true;
        }

        // Check both title and content
        return isClean(postDTO.getTitle()) && isClean(postDTO.getContent());
    }

    private static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    private static boolean isClean(String text) {
        // Normalize text: remove special characters and convert to lowercase
        String cleanedText = CLEAN_PATTERN.matcher(text).replaceAll("").toLowerCase();
        String[] words = cleanedText.split("\\s+"); // Split by whitespace

        for (String word : words) {
            if (ILLEGAL_WORDS.contains(word)) {
                return false; // Found a bad word
            }
        }

        return true; // No bad words found
    }
}
