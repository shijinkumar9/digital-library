package com.example.digitallibrary.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeminiSummaryService {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    private static final MediaType JSON
            = MediaType.get("application/json");

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String summarize(String abstractText) {

        if (abstractText == null || abstractText.isBlank()) {
            return "Summary not available.";
        }

        String prompt = """
        Summarize the following research paper abstract in 3–4 concise sentences,
        focusing on the core idea and contribution.

        Abstract:
        """ + abstractText;

        String requestBody = """
        {
          "contents": [
            {
              "role": "user",
              "parts": [
                {
                  "text": "%s"
                }
              ]
            }
          ]
        }
        """.formatted(prompt.replace("\"", "\\\""));

        Request request = new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/"
                        + model + ":generateContent?key=" + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody, JSON))
                .build();


        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {

                if (response.body() != null) {
                    System.out.println("Gemini error body: " + response.body().string());
                }
                return "AI summary unavailable.";
            }

            String responseBody = response.body().string();


            JsonNode root = objectMapper.readTree(responseBody);

            // ✅ Correct JSON path
            JsonNode candidates = root.path("candidates");
            if (!candidates.isArray() || candidates.isEmpty()) {
                return "AI summary unavailable.";
            }

            JsonNode parts = candidates.get(0)
                    .path("content")
                    .path("parts");

            if (!parts.isArray() || parts.isEmpty()) {
                return "AI summary unavailable.";
            }

            JsonNode textNode = parts.get(0).path("text");
            if (textNode.isMissingNode()) {
                return "AI summary unavailable.";
            }

            return textNode.asText();

        } catch (Exception e) {
            e.printStackTrace();   // IMPORTANT
            return "AI summary unavailable";
        }
    }
}
