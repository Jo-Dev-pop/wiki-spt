package com.example.wikispt.controller;

import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public String liste(@AuthenticationPrincipal Utilisateur utilisateur,
                        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "0") int page,
                        Model model) {

        model.addAttribute("pageNotifications",
                notificationService.findByUtilisateur(utilisateur, page, 10));
        model.addAttribute("content", "admin/fragments/notifications");
        return "admin/dashboard";
    }

    @GetMapping("/lue/{id}")
    public String marquerLue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        notificationService.marquerCommeLue(id);
        return "redirect:/admin/notifications";
    }
}