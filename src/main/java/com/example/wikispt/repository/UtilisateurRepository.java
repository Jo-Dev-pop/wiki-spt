package com.example.wikispt.repository;

import com.example.wikispt.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wikispt.enums.Role;
import java.util.List;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByRole(Role role);

    boolean existsByEmail(String email);

    Page<Utilisateur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nom,
            String prenom,
            String email,
            Pageable pageable
    );
}