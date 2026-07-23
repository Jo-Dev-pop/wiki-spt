package com.example.wikispt.dto;

import com.example.wikispt.enums.StatutArticle;
import lombok.Data;

@Data
public class ArticleDto {

    private Long id;

    private String titre;

    private String contenu;

    // Les mots-clés saisis sous la forme :
    // java,spring,thymeleaf
    private String motsCles;

    private StatutArticle statut;

    private String motifRejet;

    private Long categorieId;

    private String categorieNom;

    private Long contributeurId;

    private String contributeurNom;

}