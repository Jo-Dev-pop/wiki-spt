package com.example.wikispt.repository;

import com.example.wikispt.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Utilisateur> findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String nom,
            String email
    );
}
