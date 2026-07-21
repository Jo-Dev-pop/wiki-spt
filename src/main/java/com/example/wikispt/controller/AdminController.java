package com.example.wikispt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("content", "admin/fragments/accueil");

        return "admin/dashboard";
    }

}