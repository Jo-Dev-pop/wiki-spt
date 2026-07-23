package com.example.wikispt.controller;

import com.example.wikispt.dto.ArticleDto;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.service.ArticleService;
import com.example.wikispt.service.CategorieService;
import com.example.wikispt.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final CategorieService categorieService;
    private final UtilisateurService utilisateurService;

    @GetMapping
    public String listeArticles(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String motCle,

            Model model) {

        if (motCle != null && !motCle.isBlank()) {

            model.addAttribute(
                    "pageArticles",
                    articleService.rechercher(motCle, page, size));

        } else {

            model.addAttribute(
                    "pageArticles",
                    articleService.findAll(page, size));

        }

        model.addAttribute("motCle", motCle);

        model.addAttribute("content",
                "admin/fragments/articles");

        return "admin/dashboard";

    }

    @GetMapping("/nouveau")
    public String nouveau(Model model) {

        model.addAttribute("article", new ArticleDto());

        model.addAttribute("categories",
                categorieService.findAll());

        model.addAttribute("content",
                "admin/fragments/ajouter-article");

        return "admin/dashboard";

    }

    @PostMapping
    public String enregistrer(

            @ModelAttribute ArticleDto dto,

            @AuthenticationPrincipal Utilisateur utilisateur,

            RedirectAttributes redirectAttributes) {

        dto.setContributeurId(utilisateur.getId());

        boolean nouveau = dto.getId() == null;

        articleService.save(dto);

        redirectAttributes.addFlashAttribute(
                "success",
                nouveau ?
                        "Article enregistré avec succès."
                        :
                        "Article modifié avec succès."
        );

        return "redirect:/admin/articles";

    }

    @GetMapping("/modifier/{id}")
    public String modifier(

            @PathVariable Long id,

            Model model) {

        model.addAttribute(
                "article",
                articleService.findById(id));

        model.addAttribute(
                "categories",
                categorieService.findAll());

        model.addAttribute(
                "content",
                "admin/fragments/ajouter-article");

        return "admin/dashboard";

    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(

            @PathVariable Long id,

            RedirectAttributes redirectAttributes) {

        articleService.delete(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Article supprimé avec succès.");

        return "redirect:/admin/articles";

    }

    @GetMapping("/approuver/{id}")
    public String approuver(

            @PathVariable Long id,

            RedirectAttributes redirectAttributes) {

        articleService.approuver(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Article approuvé.");

        return "redirect:/admin/articles";

    }

    @GetMapping("/{id}")
    public String consulter(

            @PathVariable Long id,

            Model model){

        model.addAttribute(
                "article",
                articleService.findById(id));

        model.addAttribute(
                "content",
                "admin/fragments/consulter-article");

        return "admin/dashboard";

    }

    @PostMapping("/rejeter/{id}")
    public String rejeter(

            @PathVariable Long id,

            @RequestParam String motif,

            RedirectAttributes redirectAttributes) {

        articleService.rejeter(id, motif);

        redirectAttributes.addFlashAttribute(
                "success",
                "Article rejeté.");

        return "redirect:/admin/articles";

    }

}