package com.example.wikispt.service;

import com.example.wikispt.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {

    List<UtilisateurDto> findAll();

    UtilisateurDto findById(Long id);

    UtilisateurDto save(UtilisateurDto utilisateurDto);

    UtilisateurDto update(Long id, UtilisateurDto utilisateurDto);

    void delete(Long id);

}