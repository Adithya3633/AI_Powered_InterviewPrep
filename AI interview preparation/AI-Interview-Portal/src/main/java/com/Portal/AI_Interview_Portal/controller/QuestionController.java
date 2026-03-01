package com.Portal.AI_Interview_Portal.controller;



import com.Portal.AI_Interview_Portal.entity.Question;
import com.Portal.AI_Interview_Portal.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/questions")
    public String viewQuestions(@RequestParam("category") String category, Model model) {
        List<Question> questions = questionRepo.findByCategory(category);
        model.addAttribute("questions", questions);
        model.addAttribute("category", category);
        return "questions"; // this is questions.html
    }


@GetMapping("/code-editor")
public String showCodeEditor(@RequestParam("question") String question,
                             @RequestParam("category") String category,
                             Model model) {
    model.addAttribute("question", question);
    model.addAttribute("category", category);
    return "code-editor"; // this refers to code-editor.html
}
}

