package com.example.wikispt.entity;

import com.example.wikispt.enums.Role;
import com.example.wikispt.enums.StatutCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utilisateur extends AbstractAuditingEntity {

    //attributs
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutCompte statut = StatutCompte.ACTIF;

    private int tentativesEchouees = 0;

    @Column(name = "date_verrouillage")
    private Instant dateVerrouillage;

    // methodes
    public boolean estVerrouille() {
        return statut == StatutCompte.VERROUILLE;
    }

    public boolean estBloque(){
        return statut == StatutCompte.BLOQUE;
    }

    public boolean estActif() {
        return statut == StatutCompte.ACTIF;
    }

    public void verrouiller() {
        statut = StatutCompte.VERROUILLE;
        dateVerrouillage = Instant.now();
    }
}
