package com.Portal.AI_Interview_Portal.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.Portal.AI_Interview_Portal.entity.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(String category);
}
