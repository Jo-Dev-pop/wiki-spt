package com.example.wikispt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Categorie extends AbstractAuditingEntity {

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Categorie parent;

    @OneToMany(mappedBy = "parent")
    private List<Categorie> sousCategories = new ArrayList<>();

    @OneToMany(mappedBy = "categorie")
    private List<Article> articles = new ArrayList<>();

    public void ajouterArticle(Article article) {
        articles.add(article);
        article.setCategorie(this);
    }

    public void retirerArticle(Article article) {
        articles.remove(article);
        article.setCategorie(null);
    }
}