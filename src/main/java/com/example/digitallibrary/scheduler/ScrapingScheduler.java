package com.example.digitallibrary.scheduler;

import com.example.digitallibrary.scraper.ResearchPaperScraper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScrapingScheduler {

    private final ResearchPaperScraper scraper;

    @Scheduled(fixedDelay = 3600000) // every 1 hour
    public void scrapeJob() {
        scraper.scrapeWithRetry("https://arxiv.org");
    }
}
