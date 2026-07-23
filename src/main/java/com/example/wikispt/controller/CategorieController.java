package com.example.wikispt.controller;

import com.example.wikispt.dto.CategorieDto;
import com.example.wikispt.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public String listeCategories(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String motCle,

            Model model) {

        if (motCle != null && !motCle.isBlank()) {

            model.addAttribute(
                    "pageCategories",
                    categorieService.rechercher(motCle, page, size)
            );

        } else {

            model.addAttribute(
                    "pageCategories",
                    categorieService.findAll(page, size)
            );

        }

        model.addAttribute("motCle", motCle);
        model.addAttribute("content", "admin/fragments/categories");

        return "admin/dashboard";

    }

    @PostMapping
    public String enregistrer(

            @ModelAttribute CategorieDto dto,

            RedirectAttributes redirectAttributes) {

        boolean nouveau = dto.getId() == null;

        categorieService.save(dto);

        redirectAttributes.addFlashAttribute(

                "success",

                nouveau ?

                        "Catégorie ajoutée avec succès."

                        :

                        "Catégorie modifiée avec succès."

        );

        return "redirect:/admin/categories";

    }

    @GetMapping("/modifier/{id}")
    public String modifier(

            @PathVariable Long id,

            Model model) {

        model.addAttribute(

                "categorie",

                categorieService.findById(id)

        );

        model.addAttribute(
                "categories",
                categorieService.findAll()
                        .stream()
                        .filter(c -> !c.getId().equals(id))
                        .toList()
        );

        model.addAttribute(

                "content",

                "admin/fragments/ajouter-categorie"

        );

        return "admin/dashboard";

    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(

            @PathVariable Long id,

            RedirectAttributes redirectAttributes) {

        try {

            categorieService.delete(id);

            redirectAttributes.addFlashAttribute(

                    "success",

                    "Catégorie supprimée avec succès."

            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(

                    "error",

                    e.getMessage()

            );

        }

        return "redirect:/admin/categories";

    }

    @GetMapping("/nouveau")
    public String nouveau(Model model){

        model.addAttribute("categorie", new CategorieDto());

        model.addAttribute("categories",
                categorieService.findAll());

        model.addAttribute("content",
                "admin/fragments/ajouter-categorie");

        return "admin/dashboard";
    }



}