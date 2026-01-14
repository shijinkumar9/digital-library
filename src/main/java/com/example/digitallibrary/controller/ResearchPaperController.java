package com.example.digitallibrary.controller;

import com.example.digitallibrary.dto.ResearchPaperRequest;
import com.example.digitallibrary.dto.ResearchPaperResponse;
import com.example.digitallibrary.entity.ResearchPaper;
import com.example.digitallibrary.scraper.ResearchPaperScraper;
import com.example.digitallibrary.service.ResearchPaperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/papers")
@RequiredArgsConstructor
public class ResearchPaperController {

    private final ResearchPaperService service;
    private final ResearchPaperScraper scraper;

    // ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResearchPaperResponse addPaper(
            @Valid @RequestBody ResearchPaperRequest request
    ) {
        ResearchPaper paper = service.addPaper(request);
        return mapToResponse(paper);
    }

    // USER + ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<ResearchPaperResponse> getAllPapers() {
        return service.getAllPapers()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResearchPaperResponse getPaper(@PathVariable Long id) {
        return mapToResponse(service.getPaperById(id));
    }

    private ResearchPaperResponse mapToResponse(ResearchPaper paper) {
        return ResearchPaperResponse.builder()
                .id(paper.getId())
                .title(paper.getTitle())
                .authors(paper.getAuthors())
                .abstractText(paper.getAbstractText())
                .aiSummary(paper.getAiSummary())
                .publicationDate(paper.getPublicationDate())
                .sourceUrl(paper.getSourceUrl())
                .build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/search")
    public List<ResearchPaperResponse> searchPapers(
            @RequestParam String keyword
    ) {
        return service.searchPapers(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/scrape")
    public String triggerScraping() {
        scraper.scrapeWithRetry("https://arxiv.org");
        return "Scraping triggered";
    }
}
