package com.example.digitallibrary.ai;

import org.springframework.stereotype.Service;

@Service
public class SummaryService {

    public String generateSummary(String abstractText) {

        if (abstractText == null || abstractText.isBlank()) {
            return "Summary not available.";
        }

        // Simple extractive summarization:
        // Take first 2–3 sentences
        String[] sentences = abstractText.split("\\. ");

        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < Math.min(3, sentences.length); i++) {
            summary.append(sentences[i]).append(". ");
        }

        return summary.toString().trim();
    }
}
