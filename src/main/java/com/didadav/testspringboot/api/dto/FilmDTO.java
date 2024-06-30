package com.didadav.testspringboot.api.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FilmDTO {
    private String titre;
    private String description;
    private List<ActeurDTO> acteurs;
}
