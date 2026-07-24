package com.example.wikispt.service;

import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.enums.TypeAction;
import org.springframework.data.domain.Page;
import com.example.wikispt.entity.Historique;

public interface HistoriqueService {
    void enregistrer(TypeAction action, String description, Utilisateur acteur);
    Page<Historique> findAll(int page, int size);
}