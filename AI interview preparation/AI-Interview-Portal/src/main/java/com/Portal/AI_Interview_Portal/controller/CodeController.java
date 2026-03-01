package com.Portal.AI_Interview_Portal.controller;

import com.Portal.AI_Interview_Portal.entity.Submission;
import com.Portal.AI_Interview_Portal.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CodeController {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/submit-code")
    public String submitCode(@RequestParam("category") String category,
                             @RequestParam("question") String question,
                             @RequestParam("code") String code,
                             @RequestParam("language") String language,
                             @RequestParam(value = "input", required = false) String input,  // Add input parameter
                             Principal principal,
                             Model model) {

        // 🔁 Replace with your actual JDoodle credentials
        String clientId = "f0006454dcab4991f3bc9f50da7d54b4";
        String clientSecret = "7473e8a5092dec8475dfc6e88a56b1483c63dbad98a99907952c8bb16864a037";

        // Set the correct version index based on the selected language
        String versionIndex = "3"; // Default to Java 11 for now

        // Determine versionIndex based on language
        if ("python".equalsIgnoreCase(language)) {
            versionIndex = "3"; // Python 3
        } else if ("java".equalsIgnoreCase(language)) {
            versionIndex = "3"; // Java 11
        } else if ("c".equalsIgnoreCase(language)) {
            versionIndex = "2"; // C (GCC)
        }

        // JDoodle request
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("clientId", clientId);
        requestMap.put("clientSecret", clientSecret);
        requestMap.put("script", code);
        requestMap.put("language", language); // Use dynamic language
        requestMap.put("versionIndex", versionIndex);

        // Include the user input (if provided) for programs that need it
        if (input != null && !input.trim().isEmpty()) {
            requestMap.put("stdin", input); // Pass input to JDoodle API
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);

        String output;
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.jdoodle.com/v1/execute", entity, Map.class
            );
            output = (String) response.getBody().get("output");
        } catch (Exception e) {
            output = "Execution Error: " + e.getMessage();
        }

        // Save submission to DB
        Submission submission = new Submission();
        submission.setUsername(principal.getName());
        submission.setQuestion(question);
        submission.setCode(code);
        submission.setOutput(output);
        submission.setTimestamp(LocalDateTime.now());
        submissionRepository.save(submission);

        // Send the results to frontend
        model.addAttribute("category", category);
        model.addAttribute("question", question);
        model.addAttribute("code", code);
        model.addAttribute("input", input);  // Send input to the frontend for display
        model.addAttribute("output", output);

        return "code-result";  // Show the results on the code-result page
    }
}
