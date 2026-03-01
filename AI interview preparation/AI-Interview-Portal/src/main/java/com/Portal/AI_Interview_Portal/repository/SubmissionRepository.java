package com.Portal.AI_Interview_Portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Portal.AI_Interview_Portal.entity.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUsername(String username);
}
