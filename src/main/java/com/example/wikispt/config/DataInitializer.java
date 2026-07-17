package com.example.wikispt.config;

import com.example.wikispt.entity.Utilisateur;
import com.example.wikispt.enums.Role;
import com.example.wikispt.enums.StatutCompte;
import com.example.wikispt.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (utilisateurRepository.existsByEmail("admin@laposte.tg")) {
            return;
        }

        Utilisateur admin = new Utilisateur();

        admin.setNom("Administrateur");
        admin.setPrenom("Système");
        admin.setEmail("admin@laposte.tg");

        admin.setMotDePasse(passwordEncoder.encode("Admin123"));

        admin.setRole(Role.ADMINISTRATEUR);

        admin.setStatut(StatutCompte.ACTIF);

        utilisateurRepository.save(admin);

        System.out.println("======================================");
        System.out.println(" Administrateur créé avec succès !");
        System.out.println(" Email : admin@laposte.tg");
        System.out.println(" Mot de passe : Admin123");
        System.out.println("======================================");
    }
}