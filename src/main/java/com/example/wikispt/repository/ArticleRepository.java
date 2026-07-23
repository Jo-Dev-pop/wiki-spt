package com.example.wikispt.repository;

import com.example.wikispt.entity.Article;
import com.example.wikispt.entity.Categorie;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.enums.StatutArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByTitreContainingIgnoreCase(
            String titre,
            Pageable pageable
    );

    Page<Article> findByCategorie(
            Categorie categorie,
            Pageable pageable
    );

    Page<Article> findByStatut(
            StatutArticle statut,
            Pageable pageable
    );

    Page<Article> findByContributeur(
            Utilisateur contributeur,
            Pageable pageable
    );

}