package com.example.wikispt.repository;

import com.example.wikispt.entity.Historique;
import com.example.wikispt.enums.TypeAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {
    Page<Historique> findAllByOrderByCreatedAtDesc(Pageable pageable);

    long countByActionAndCreatedAtAfter(TypeAction action, Instant instant);
}