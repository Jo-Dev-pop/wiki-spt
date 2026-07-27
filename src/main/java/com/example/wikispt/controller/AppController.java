package com.example.wikispt.controller;

import com.example.wikispt.dto.ArticleDto;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.service.ArticleService;
import com.example.wikispt.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppController {

    private final ArticleService articleService;
    private final CategorieService categorieService;

    @GetMapping("/accueil")
    public String accueil(Model model) {
        model.addAttribute("content", "app/fragments/accueil");
        return "app/layout";
    }

    @GetMapping("/articles")
    public String articles(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(required = false) String motCle,
                           Model model) {

        model.addAttribute("pageArticles",
                (motCle != null && !motCle.isBlank())
                        ? articleService.rechercherPublies(motCle, page, 9)
                        : articleService.findAllPublies(page, 9));

        model.addAttribute("motCle", motCle);
        model.addAttribute("content", "app/fragments/articles");
        return "app/layout";
    }

    @GetMapping("/articles/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.findById(id));
        model.addAttribute("content", "app/fragments/article-detail");
        return "app/layout";
    }

    @GetMapping("/articles/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("article", new ArticleDto());
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("content", "app/fragments/article-form");
        return "app/layout";
    }

    @PostMapping("/articles")
    public String enregistrer(@ModelAttribute ArticleDto dto,
                              @AuthenticationPrincipal Utilisateur utilisateur,
                              RedirectAttributes redirectAttributes) {

        dto.setContributeurId(utilisateur.getId());
        articleService.save(dto);

        redirectAttributes.addFlashAttribute("success",
                "Votre article a été soumis. Il sera visible après validation par un administrateur.");

        return "redirect:/app/articles";
    }
}