package com.example.wikispt.service;

import com.example.wikispt.dto.ArticleDto;
import org.springframework.data.domain.Page;

public interface ArticleService {

    Page<ArticleDto> findAll(int page, int size);

    Page<ArticleDto> rechercher(String motCle, int page, int size);

    ArticleDto findById(Long id);

    ArticleDto save(ArticleDto dto);

    void delete(Long id);

    void approuver(Long id);

    void rejeter(Long id, String motif);

}