package com.example.wikispt.service.impl;

import com.example.wikispt.dto.CategorieDto;
import com.example.wikispt.entity.Categorie;
import com.example.wikispt.mapper.CategorieMapper;
import com.example.wikispt.repository.CategorieRepository;
import com.example.wikispt.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;

    @Override
    public Page<CategorieDto> findAll(int page, int size) {

        return categorieRepository.findAll(PageRequest.of(page, size))
                .map(categorieMapper::toDto);

    }

    @Override
    public List<CategorieDto> findAll() {

        return categorieRepository.findAll()
                .stream()
                .map(categorieMapper::toDto)
                .toList();

    }

    @Override
    public CategorieDto findById(Long id) {

        return categorieMapper.toDto(

                categorieRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Catégorie introuvable"))

        );

    }

    @Override
    public CategorieDto save(CategorieDto dto) {

        Categorie categorie;

        if (dto.getId() == null) {

            categorie = new Categorie();

        } else {

            categorie = categorieRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        }

        categorie.setNom(dto.getNom());
        categorie.setDescription(dto.getDescription());

        if (dto.getParentId() != null) {

            Categorie parent = categorieRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Catégorie parente introuvable"));

            categorie.setParent(parent);

        } else {

            categorie.setParent(null);

        }

        categorie = categorieRepository.save(categorie);

        return categorieMapper.toDto(categorie);

    }

    @Override
    public void delete(Long id) {

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        if (!categorie.getArticles().isEmpty()) {

            throw new RuntimeException("Impossible de supprimer une catégorie contenant des articles.");

        }

        categorieRepository.delete(categorie);

    }

    @Override
    public Page<CategorieDto> rechercher(String motCle, int page, int size) {

        return categorieRepository
                .findByNomContainingIgnoreCase(motCle, PageRequest.of(page, size))
                .map(categorieMapper::toDto);

    }

}