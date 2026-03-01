package com.Portal.AI_Interview_Portal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Portal.AI_Interview_Portal.entity.user;
import com.Portal.AI_Interview_Portal.service.userservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class AuthController {

    @Autowired
    private userservice userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new user());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") user user) {
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        String username = principal.getName(); // Gets the logged-in user's username (email or name)
        model.addAttribute("username", username);
        return "dashboard";
    }
    @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpServletResponse response) {
            // Custom logout logic (optional)
            new SecurityContextLogoutHandler().logout(request, response, null);  // Logs out the user
            return "redirect:/";  // Redirect to index page after logout
        }
    }

   

