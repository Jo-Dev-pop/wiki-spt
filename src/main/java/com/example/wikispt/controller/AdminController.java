package com.example.wikispt.controller;

import com.example.wikispt.repository.ArticleRepository;
import com.example.wikispt.repository.CategorieRepository;
import com.example.wikispt.repository.UtilisateurRepository;
import com.example.wikispt.enums.StatutArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UtilisateurRepository utilisateurRepository;
    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalUtilisateurs", utilisateurRepository.count());
        model.addAttribute("totalArticles", articleRepository.count());
        model.addAttribute("totalCategories", categorieRepository.count());
        model.addAttribute("articlesEnAttente",
                articleRepository.findByStatut(StatutArticle.EN_ATTENTE,
                        org.springframework.data.domain.PageRequest.of(0, 1)).getTotalElements());

        model.addAttribute("content", "admin/fragments/accueil");

        return "admin/dashboard";
    }
}