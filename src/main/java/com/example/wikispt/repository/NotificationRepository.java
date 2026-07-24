package com.example.wikispt.repository;

import com.example.wikispt.entity.Notification;
import com.example.wikispt.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUtilisateurOrderByCreatedAtDesc(Utilisateur utilisateur, Pageable pageable);
    long countByUtilisateurAndLuFalse(Utilisateur utilisateur);
}