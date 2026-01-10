package com.example.digitallibrary.service;

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

    public ResearchPaper addPaper(ResearchPaperRequest request) {

        ResearchPaper paper = ResearchPaper.builder()
                .title(request.getTitle())
                .authors(request.getAuthors())
                .abstractText(request.getAbstractText())
                .publicationDate(request.getPublicationDate())
                .sourceUrl(request.getSourceUrl())
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(paper);
    }

    public List<ResearchPaper> getAllPapers() {
        return repository.findAll();
    }

    public ResearchPaper getPaperById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paper not found"));
    }

    public List<ResearchPaper> searchPapers(String keyword) {
        return repository.searchByKeyword(keyword);
    }

}
