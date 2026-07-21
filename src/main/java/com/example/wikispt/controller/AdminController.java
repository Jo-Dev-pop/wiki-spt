package com.example.wikispt.controller;

import com.example.wikispt.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UtilisateurService utilisateurService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("pageUtilisateurs",
                utilisateurService.findAll(0, 5));

        model.addAttribute("content",
                "admin/fragments/utilisateurs");

        return "admin/dashboard";
    }
}