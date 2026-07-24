package com.example.wikispt.repository;

import com.example.wikispt.entity.Historique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {
    Page<Historique> findAllByOrderByCreatedAtDesc(Pageable pageable);
}