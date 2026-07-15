package com.example.wikispt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categorie extends AbstractAuditingEntity{

    //attributs

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Categorie sousCategorie;   // relation récursive

    @OneToMany(mappedBy = "categorie")
    private List<Article> articles = new ArrayList<>();

    //methodes

    public void ajouterArticle(Article article) {
        articles.add(article);
        article.setCategorie(this);
    }

    public void retirerArticle(Article article) {
        articles.remove(article);
        article.setCategorie(null);
    }
}
