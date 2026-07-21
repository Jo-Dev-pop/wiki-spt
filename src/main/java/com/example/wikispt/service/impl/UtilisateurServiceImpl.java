package com.example.wikispt.service.impl;

import com.example.wikispt.dto.UtilisateurDto;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.mapper.UtilisateurMapper;
import com.example.wikispt.repository.UtilisateurRepository;
import com.example.wikispt.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.wikispt.enums.StatutCompte;

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

        Utilisateur utilisateur;

        if (utilisateurDto.getId() != null) {

            utilisateur = utilisateurRepository.findById(utilisateurDto.getId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

            utilisateur.setNom(utilisateurDto.getNom());
            utilisateur.setPrenom(utilisateurDto.getPrenom());
            utilisateur.setEmail(utilisateurDto.getEmail());
            utilisateur.setRole(utilisateurDto.getRole());
            utilisateur.setStatut(utilisateurDto.getStatut());

        } else {

            utilisateur = utilisateurMapper.toEntity(utilisateurDto);

        }

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
    public void desactiver(Long id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        utilisateur.setStatut(StatutCompte.BLOQUE);

        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void reactiver(Long id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        utilisateur.setStatut(StatutCompte.ACTIF);

        utilisateurRepository.save(utilisateur);
    }
}