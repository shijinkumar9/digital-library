package com.example.digitallibrary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "research_papers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResearchPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String authors;

    @Column(nullable = false, length = 5000)
    private String abstractText;

    private LocalDate publicationDate;

    @Column(nullable = false)
    private String sourceUrl;

    private LocalDateTime createdAt;
}
