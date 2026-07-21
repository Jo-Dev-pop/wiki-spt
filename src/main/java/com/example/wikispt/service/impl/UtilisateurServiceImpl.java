package com.example.wikispt.service.impl;

import com.example.wikispt.dto.UtilisateurDto;
import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.enums.StatutCompte;
import com.example.wikispt.mapper.UtilisateurMapper;
import com.example.wikispt.repository.UtilisateurRepository;
import com.example.wikispt.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UtilisateurDto> findAll(int page, int size) {

        return utilisateurRepository
                .findAll(PageRequest.of(page, size))
                .map(utilisateurMapper::toDto);

    }

    @Override
    public Page<UtilisateurDto> rechercher(String motCle, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return utilisateurRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        motCle,
                        motCle,
                        motCle,
                        pageable
                )
                .map(utilisateurMapper::toDto);
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

            if (utilisateur.getMotDePasse() != null) {
                utilisateur.setMotDePasse(
                        passwordEncoder.encode(utilisateur.getMotDePasse())
                );
            }

            if (utilisateur.getStatut() == null) {
                utilisateur.setStatut(StatutCompte.ACTIF);
            }
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
        utilisateur.setRole(utilisateurDto.getRole());
        utilisateur.setStatut(utilisateurDto.getStatut());

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