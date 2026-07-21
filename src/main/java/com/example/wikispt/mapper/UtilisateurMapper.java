package com.example.wikispt.mapper;

import com.example.wikispt.dto.UtilisateurDto;
import com.example.wikispt.entity.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    UtilisateurDto toDto(Utilisateur utilisateur);

    Utilisateur toEntity(UtilisateurDto utilisateurDto);

}