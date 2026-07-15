package com.example.wikispt.entity;

import com.example.wikispt.enums.StatutArticle;
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
public class Article extends AbstractAuditingEntity {

    //attributs
    @Column(nullable = false, length = 200)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @ElementCollection
    private List<String> motsCles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutArticle statut = StatutArticle.EN_ATTENTE;

    @Column(length = 500)
    private String motifRejet;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "contributeur_id", nullable = false)
    private Utilisateur contributeur;

    //methodes

    public void publier() {
        this.statut = StatutArticle.PUBLIE;
        this.motifRejet = null;
    }

    public void rejeter(String motif) {
        this.statut = StatutArticle.REJETE;
        this.motifRejet = motif;
    }

    public void desactiver() {
        this.statut = StatutArticle.DESACTIVE;
    }

    public void modifierContenu(String titre, String contenu) {
        this.titre = titre;
        this.contenu = contenu;
    }
}