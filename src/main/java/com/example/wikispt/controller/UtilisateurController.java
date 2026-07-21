package com.example.wikispt.controller;

import com.example.wikispt.dto.UtilisateurDto;
import com.example.wikispt.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public String listeUtilisateurs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String motCle,
            Model model) {

        if (motCle != null && !motCle.isBlank()) {

            model.addAttribute(
                    "pageUtilisateurs",
                    utilisateurService.rechercher(motCle, page, size)
            );

        } else {

            model.addAttribute(
                    "pageUtilisateurs",
                    utilisateurService.findAll(page, size)
            );
        }

        model.addAttribute("motCle", motCle);
        model.addAttribute("content", "admin/fragments/utilisateurs");

        return "admin/dashboard";
    }

    @GetMapping("/nouveau")
    public String nouveauUtilisateur(Model model) {

        model.addAttribute("utilisateur", new UtilisateurDto());

        model.addAttribute("content",
                "admin/fragments/ajouter-utilisateur");

        return "admin/dashboard";
    }

    @PostMapping
    public String ajouterUtilisateur(
            @ModelAttribute("utilisateur") UtilisateurDto utilisateurDto,
            RedirectAttributes redirectAttributes) {

        boolean nouveau = utilisateurDto.getId() == null;

        utilisateurService.save(utilisateurDto);

        if (nouveau) {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Utilisateur ajouté avec succès."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Utilisateur modifié avec succès."
            );
        }

        return "redirect:/admin/utilisateurs";
    }

    @GetMapping("/modifier/{id}")
    public String modifierUtilisateur(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "utilisateur",
                utilisateurService.findById(id)
        );

        model.addAttribute(
                "content",
                "admin/fragments/ajouter-utilisateur"
        );

        return "admin/dashboard";
    }

    @GetMapping("/desactiver/{id}")
    public String desactiverUtilisateur(
            @PathVariable Long id,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        UtilisateurDto utilisateur = utilisateurService.findById(id);

        if (authentication.getName().equals(utilisateur.getEmail())) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Vous ne pouvez pas désactiver votre propre compte."
            );

            return "redirect:/admin/utilisateurs";
        }

        utilisateurService.desactiver(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Utilisateur désactivé avec succès."
        );

        return "redirect:/admin/utilisateurs";
    }

    @GetMapping("/reactiver/{id}")
    public String reactiverUtilisateur(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        utilisateurService.reactiver(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Utilisateur réactivé avec succès."
        );

        return "redirect:/admin/utilisateurs";
    }
}