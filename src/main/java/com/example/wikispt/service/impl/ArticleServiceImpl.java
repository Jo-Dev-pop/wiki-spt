package com.example.wikispt.service.impl;

import com.example.wikispt.dto.ArticleDto;
import com.example.wikispt.entity.Article;
import com.example.wikispt.entity.Categorie;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.enums.Role;
import com.example.wikispt.enums.StatutArticle;
import com.example.wikispt.enums.TypeAction;
import com.example.wikispt.mapper.ArticleMapper;
import com.example.wikispt.repository.ArticleRepository;
import com.example.wikispt.repository.CategorieRepository;
import com.example.wikispt.repository.UtilisateurRepository;
import com.example.wikispt.service.ArticleService;
import com.example.wikispt.service.HistoriqueService;
import com.example.wikispt.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ArticleMapper articleMapper;
    private final NotificationService notificationService;
    private final HistoriqueService historiqueService;


    @Override
    public Page<ArticleDto> findAll(int page, int size) {

        return articleRepository
                .findAll(PageRequest.of(page, size))
                .map(articleMapper::toDto);

    }

    @Override
    public Page<ArticleDto> rechercher(String motCle, int page, int size) {

        return articleRepository
                .findByTitreContainingIgnoreCase(
                        motCle,
                        PageRequest.of(page, size)
                )
                .map(articleMapper::toDto);

    }

    @Override
    public ArticleDto findById(Long id) {

        return articleMapper.toDto(

                articleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Article introuvable"))

        );

    }

    @Override
    public ArticleDto save(ArticleDto dto) {

        Article article;

        if (dto.getId() == null) {

            article = new Article();

        } else {

            article = articleRepository.findById(dto.getId())
                    .orElseThrow(() ->
                            new RuntimeException("Article introuvable"));

        }

        article.setTitre(dto.getTitre());
        article.setContenu(dto.getContenu());

        article.setMotsCles(

                Arrays.stream(dto.getMotsCles().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList())
        );

        Categorie categorie = categorieRepository
                .findById(dto.getCategorieId())
                .orElseThrow(() ->
                        new RuntimeException("Catégorie introuvable"));

        article.setCategorie(categorie);

        Utilisateur contributeur = utilisateurRepository
                .findById(dto.getContributeurId())
                .orElseThrow(() ->
                        new RuntimeException("Contributeur introuvable"));

        article.setContributeur(contributeur);

        if (article.getStatut() == null) {

            article.setStatut(StatutArticle.EN_ATTENTE);

        }

        article = articleRepository.save(article);

        boolean nouveauArticle = (dto.getId() == null);
        article = articleRepository.save(article);

        if (contributeur.getRole() != Role.ADMINISTRATEUR) {
            notificationService.notifierSoumissionAuxAdmins(article, nouveauArticle);
            historiqueService.enregistrer(
                    nouveauArticle ? TypeAction.SOUMISSION_ARTICLE : TypeAction.MODIFICATION_ARTICLE,
                    (nouveauArticle ? "Soumission" : "Modification") + " de l'article « " + article.getTitre() + " »",
                    contributeur
            );
        }

        return articleMapper.toDto(article);

    }

    @Override
    public void delete(Long id) {

        articleRepository.deleteById(id);

    }

    @Override
    public void approuver(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article introuvable"));

        article.publier();
        articleRepository.save(article);

        notificationService.notifierPublication(article);
        historiqueService.enregistrer(
                TypeAction.PUBLICATION_ARTICLE,
                "Publication de l'article « " + article.getTitre() + " »",
                utilisateurConnecteCourant() // voir ci-dessous
        );
    }

    @Override
    public void rejeter(Long id, String motif) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article introuvable"));

        article.rejeter(motif);
        articleRepository.save(article);

        notificationService.notifierRejet(article, motif);
        historiqueService.enregistrer(
                TypeAction.REJET_ARTICLE,
                "Rejet de l'article « " + article.getTitre() + " » : " + motif,
                utilisateurConnecteCourant()
        );
    }

    // petit helper pour récupérer l'admin qui vient d'agir
    private Utilisateur utilisateurConnecteCourant() {
        var auth = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

}