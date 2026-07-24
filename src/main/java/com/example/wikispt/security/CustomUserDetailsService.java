package com.example.wikispt.security;

import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        if (utilisateur.estVerrouille() && utilisateur.getDateVerrouillage() != null) {
            Duration ecoule = Duration.between(utilisateur.getDateVerrouillage(), Instant.now());
            if (ecoule.compareTo(Duration.ofMinutes(5)) >= 0) {
                utilisateur.deverrouiller();
                utilisateurRepository.save(utilisateur);
            }
        }

        return utilisateur;
    }
}