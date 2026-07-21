package com.example.wikispt.service.impl;

import com.example.wikispt.dto.UtilisateurDto;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.mapper.UtilisateurMapper;
import com.example.wikispt.repository.UtilisateurRepository;
import com.example.wikispt.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateurMapper::toDto)
                .toList();
    }

    @Override
    public UtilisateurDto findById(Long id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {

        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        utilisateur = utilisateurRepository.save(utilisateur);

        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public UtilisateurDto update(Long id, UtilisateurDto utilisateurDto) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());

        utilisateur = utilisateurRepository.save(utilisateur);

        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public void delete(Long id) {

        utilisateurRepository.deleteById(id);

    }
}