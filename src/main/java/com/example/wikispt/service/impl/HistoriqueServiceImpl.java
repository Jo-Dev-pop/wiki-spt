package com.example.wikispt.service.impl;

import com.example.wikispt.entity.Historique;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.enums.TypeAction;
import com.example.wikispt.repository.HistoriqueRepository;
import com.example.wikispt.service.HistoriqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoriqueServiceImpl implements HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    @Override
    public void enregistrer(TypeAction action, String description, Utilisateur acteur) {
        Historique historique = new Historique();
        historique.setAction(action);
        historique.setDescription(description);
        historique.setActeur(acteur);
        historiqueRepository.save(historique);
    }

    @Override
    public Page<Historique> findAll(int page, int size) {
        return historiqueRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
    }
}