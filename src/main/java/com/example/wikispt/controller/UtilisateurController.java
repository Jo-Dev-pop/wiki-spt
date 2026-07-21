package com.example.wikispt.controller;

import com.example.wikispt.dto.UtilisateurDto;
import com.example.wikispt.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping("/nouveau")
    public String nouveauUtilisateur(Model model) {

        model.addAttribute("utilisateur", new UtilisateurDto());

        model.addAttribute("content", "admin/fragments/ajouter-utilisateur");

        return "admin/dashboard";
    }

    @GetMapping
    public String listeUtilisateurs(Model model) {

        model.addAttribute("utilisateurs", utilisateurService.findAll());

        model.addAttribute("content", "admin/fragments/utilisateurs");

        return "admin/dashboard";
    }

    @PostMapping
    public String ajouterUtilisateur(@ModelAttribute("utilisateur") UtilisateurDto utilisateurDto) {

        utilisateurService.save(utilisateurDto);

        return "redirect:/admin/utilisateurs";
    }

}