package com.example.wikispt.mapper;

import com.example.wikispt.dto.ArticleDto;
import com.example.wikispt.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target = "categorieId", source = "categorie.id")
    @Mapping(target = "categorieNom", source = "categorie.nom")
    @Mapping(target = "contributeurId", source = "contributeur.id")
    @Mapping(target = "contributeurNom", expression = "java(article.getContributeur().getNom() + \" \" + article.getContributeur().getPrenom())")
    @Mapping(target = "motsCles", expression = "java(String.join(\",\", article.getMotsCles()))")
    ArticleDto toDto(Article article);

    @Mapping(target = "categorie", ignore = true)
    @Mapping(target = "contributeur", ignore = true)
    @Mapping(target = "motsCles", ignore = true)

    // hérités de AbstractAuditingEntity
    @Mapping(target = "trackingId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)

    Article toEntity(ArticleDto dto);

}