package com.example.digitallibrary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResearchPaperRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String authors;

    @NotBlank
    private String abstractText;

    private LocalDate publicationDate;

    @NotBlank
    private String sourceUrl;
}
