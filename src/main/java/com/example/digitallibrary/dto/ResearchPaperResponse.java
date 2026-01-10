package com.example.digitallibrary.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ResearchPaperResponse {

    private Long id;
    private String title;
    private String authors;
    private String abstractText;
    private LocalDate publicationDate;
    private String sourceUrl;
}
