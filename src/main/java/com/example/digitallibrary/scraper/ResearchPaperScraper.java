package com.example.digitallibrary.scraper;

import com.example.digitallibrary.entity.ResearchPaper;
import com.example.digitallibrary.repository.ResearchPaperRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResearchPaperScraper {

    private final ResearchPaperRepository repository;

    public void scrapeWithRetry(String url) {
        int attempts = 0;
        int maxRetries = 3;

        while (attempts < maxRetries) {
            try {
                scrape(url);
                return; // success
            } catch (Exception e) {
                attempts++;
                System.err.println("Scrape failed. Attempt " + attempts);
                if (attempts == maxRetries) {
                    System.err.println("Scraping failed permanently: " + e.getMessage());
                }
            }
        }
    }

    private void scrape(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        // Example selectors (depends on site)
        String title = doc.select("title").text();

        ResearchPaper paper = ResearchPaper.builder()
                .title(title)
                .authors("Unknown")
                .abstractText("Fetched from scraping")
                .sourceUrl(url)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(paper);
    }
}
