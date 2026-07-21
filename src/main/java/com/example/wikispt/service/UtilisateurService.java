package com.example.wikispt.service;

import com.example.wikispt.dto.UtilisateurDto;
import org.springframework.data.domain.Page;

public interface UtilisateurService {

    Page<UtilisateurDto> findAll(int page, int size);

    Page<UtilisateurDto> rechercher(String motCle, int page, int size);

    UtilisateurDto findById(Long id);

    UtilisateurDto save(UtilisateurDto utilisateurDto);

    UtilisateurDto update(Long id, UtilisateurDto utilisateurDto);

    void desactiver(Long id);

    void reactiver(Long id);
}