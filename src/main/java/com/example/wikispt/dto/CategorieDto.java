package com.example.wikispt.dto;

import lombok.Data;

@Data
public class CategorieDto {

    private Long id;

    private String nom;

    private String description;

    private Long parentId;

    private String parentNom;

}