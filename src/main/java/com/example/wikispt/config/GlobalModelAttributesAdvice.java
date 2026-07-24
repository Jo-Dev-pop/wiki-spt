package com.example.wikispt.config;

import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributesAdvice {

    private final NotificationService notificationService;

    @ModelAttribute("utilisateurConnecte")
    public Utilisateur utilisateurConnecte(@AuthenticationPrincipal Utilisateur utilisateur) {
        return utilisateur;
    }

    @ModelAttribute("notificationsNonLues")
    public long notificationsNonLues(@AuthenticationPrincipal Utilisateur utilisateur) {
        if (utilisateur == null) return 0;
        return notificationService.compterNonLues(utilisateur);
    }
}