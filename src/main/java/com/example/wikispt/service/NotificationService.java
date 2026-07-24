package com.example.wikispt.service;

import com.example.wikispt.entity.Article;
import com.example.wikispt.entity.Notification;
import com.example.wikispt.entity.Utilisateur;
import org.springframework.data.domain.Page;

public interface NotificationService {
    void notifierSoumissionAuxAdmins(Article article, boolean nouveauArticle);
    void notifierPublication(Article article);
    void notifierRejet(Article article, String motif);
    Page<Notification> findByUtilisateur(Utilisateur utilisateur, int page, int size);
    long compterNonLues(Utilisateur utilisateur);
    void marquerCommeLue(Long id);
}