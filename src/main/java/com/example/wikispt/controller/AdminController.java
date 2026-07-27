package com.example.wikispt.controller;

import com.example.wikispt.enums.StatutArticle;
import com.example.wikispt.enums.TypeAction;
import com.example.wikispt.repository.ArticleRepository;
import com.example.wikispt.repository.CategorieRepository;
import com.example.wikispt.repository.HistoriqueRepository;
import com.example.wikispt.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UtilisateurRepository utilisateurRepository;
    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final HistoriqueRepository historiqueRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        Instant ilYaSeptJours = Instant.now().minus(7, ChronoUnit.DAYS);

        model.addAttribute("totalUtilisateurs", utilisateurRepository.count());
        model.addAttribute("totalArticles", articleRepository.count());
        model.addAttribute("totalCategories", categorieRepository.count());
        model.addAttribute("articlesEnAttente", articleRepository.countByStatut(StatutArticle.EN_ATTENTE));
        model.addAttribute("articlesRejetes", articleRepository.countByStatut(StatutArticle.REJETE));
        model.addAttribute("utilisateursCetteSemaine", utilisateurRepository.countByCreatedAtAfter(ilYaSeptJours));
        model.addAttribute("modificationsCetteSemaine",
                historiqueRepository.countByActionAndCreatedAtAfter(TypeAction.MODIFICATION_ARTICLE, ilYaSeptJours));

        model.addAttribute("content", "admin/fragments/accueil");

        return "admin/dashboard";
    }
}