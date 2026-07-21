package com.example.wikispt.dto;

import com.example.wikispt.enums.Role;
import com.example.wikispt.enums.StatutCompte;
import lombok.Data;

@Data
public class UtilisateurDto {

    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private String motDePasse;

    private Role role;

    private StatutCompte statut;
}