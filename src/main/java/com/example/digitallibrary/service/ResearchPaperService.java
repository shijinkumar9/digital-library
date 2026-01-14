package com.example.digitallibrary.service;

import com.example.digitallibrary.ai.GeminiSummaryService;
import com.example.digitallibrary.dto.ResearchPaperRequest;
import com.example.digitallibrary.entity.ResearchPaper;
import com.example.digitallibrary.repository.ResearchPaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchPaperService {

    private final ResearchPaperRepository repository;
    private final GeminiSummaryService geminiSummaryService;

    /**
     * Add a new research paper.
     * - Generates AI summary using Gemini
     * - Stores summary along with metadata
     */
    public ResearchPaper addPaper(ResearchPaperRequest request) {

        // Generate AI-based summary using Gemini
        String summary = geminiSummaryService.summarize(
                request.getAbstractText()
        );

        ResearchPaper paper = ResearchPaper.builder()
                .title(request.getTitle())
                .authors(request.getAuthors())
                .abstractText(request.getAbstractText())
                .publicationDate(request.getPublicationDate())
                .sourceUrl(request.getSourceUrl())
                .aiSummary(summary)
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(paper);
    }

    /**
     * Fetch all research papers
     */
    public List<ResearchPaper> getAllPapers() {
        return repository.findAll();
    }

    /**
     * Fetch a research paper by ID
     */
    public ResearchPaper getPaperById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Research paper not found with id: " + id)
                );
    }

    /**
     * Search papers by keyword (title, authors, abstract)
     */
    public List<ResearchPaper> searchPapers(String keyword) {
        return repository.searchByKeyword(keyword);
    }
}
