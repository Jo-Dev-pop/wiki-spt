package com.example.wikispt.mapper;

import com.example.wikispt.dto.CategorieDto;
import com.example.wikispt.entity.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategorieMapper {

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentNom", source = "parent.nom")
    CategorieDto toDto(Categorie categorie);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "sousCategories", ignore = true)
    @Mapping(target = "articles", ignore = true)

    @Mapping(target = "trackingId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)

    Categorie toEntity(CategorieDto dto);

}