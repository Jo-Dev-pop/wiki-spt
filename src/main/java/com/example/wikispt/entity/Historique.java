package com.example.wikispt.entity;

import com.example.wikispt.enums.TypeAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Historique extends AbstractAuditingEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TypeAction action;

    @Column(nullable = false, length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "acteur_id")
    private Utilisateur acteur;
}