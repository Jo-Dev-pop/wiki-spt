package com.example.wikispt.service;

import com.example.wikispt.dto.CategorieDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategorieService {

    Page<CategorieDto> findAll(int page, int size);

    List<CategorieDto> findAll();

    CategorieDto findById(Long id);

    CategorieDto save(CategorieDto dto);

    void delete(Long id);

    Page<CategorieDto> rechercher(String motCle, int page, int size);

}