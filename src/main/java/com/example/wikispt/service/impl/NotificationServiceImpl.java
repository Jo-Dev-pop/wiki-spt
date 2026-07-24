package com.example.wikispt.service.impl;

import com.example.wikispt.entity.Article;
import com.example.wikispt.entity.Notification;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.enums.Role;
import com.example.wikispt.enums.TypeNotification;
import com.example.wikispt.repository.NotificationRepository;
import com.example.wikispt.repository.UtilisateurRepository;
import com.example.wikispt.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public void notifierSoumissionAuxAdmins(Article article, boolean nouveauArticle) {
        String message = String.format(
                "%s %s l'article « %s » (catégorie : %s).",
                article.getContributeur().getPrenom() + " " + article.getContributeur().getNom(),
                nouveauArticle ? "a soumis un nouvel article :" : "a modifié l'article :",
                article.getTitre(),
                article.getCategorie().getNom()
        );

        for (Utilisateur admin : utilisateurRepository.findByRole(Role.ADMINISTRATEUR)) {
            creer(admin, TypeNotification.SOUMISSION, message);
        }
    }

    @Override
    public void notifierPublication(Article article) {
        String message = "Votre article « " + article.getTitre() + " » a été validé et publié.";
        creer(article.getContributeur(), TypeNotification.PUBLICATION, message);
    }

    @Override
    public void notifierRejet(Article article, String motif) {
        String message = "Votre article « " + article.getTitre() + " » a été rejeté. Motif : " + motif;
        creer(article.getContributeur(), TypeNotification.REJET, message);
    }

    private void creer(Utilisateur destinataire, TypeNotification type, String message) {
        Notification notification = new Notification();
        notification.setUtilisateur(destinataire);
        notification.setType(type);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> findByUtilisateur(Utilisateur utilisateur, int page, int size) {
        return notificationRepository.findByUtilisateurOrderByCreatedAtDesc(
                utilisateur, PageRequest.of(page, size));
    }

    @Override
    public long compterNonLues(Utilisateur utilisateur) {
        return notificationRepository.countByUtilisateurAndLuFalse(utilisateur);
    }

    @Override
    public void marquerCommeLue(Long id) {
        notificationRepository.findById(id).ifPresent(n -> {
            n.marquerCommeLue();
            notificationRepository.save(n);
        });
    }
}