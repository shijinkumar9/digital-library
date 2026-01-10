package com.example.digitallibrary.repository;

import com.example.digitallibrary.entity.ResearchPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResearchPaperRepository extends JpaRepository<ResearchPaper, Long> {
    @Query("""
        SELECT r FROM ResearchPaper r
        WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(r.authors) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(r.abstractText) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<ResearchPaper> searchByKeyword(@Param("keyword") String keyword);
}
