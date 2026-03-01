package com.Portal.AI_Interview_Portal.controller;

import com.Portal.AI_Interview_Portal.entity.Submission;  // Ensure you import the correct entity
import com.Portal.AI_Interview_Portal.repository.SubmissionRepository;  // Import your repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class SubmissionsController {

    @Autowired
    private SubmissionRepository submissionRepository;  // Inject the repository

    // Map the '/submissions' endpoint
    @GetMapping("/submissions")
    public String viewSubmissions(Model model, Principal principal) {
        // Fetch the submissions based on the logged-in user's name
        List<Submission> submissions = submissionRepository.findByUsername(principal.getName());

        // Add the submissions to the model
        model.addAttribute("submissions", submissions);

        // Return the name of the Thymeleaf template (submissions.html)
        return "submissions";
    }
}
