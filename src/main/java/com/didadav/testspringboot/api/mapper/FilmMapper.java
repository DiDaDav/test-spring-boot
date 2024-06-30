package com.didadav.testspringboot.api.mapper;

import com.didadav.testspringboot.api.dto.ActeurDTO;
import com.didadav.testspringboot.api.dto.FilmDTO;
import com.didadav.testspringboot.entity.Acteur;
import com.didadav.testspringboot.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FilmMapper {
    @Mapping(target = "id", ignore = true)
    Film filmDtoToFilm(FilmDTO filmDTO);

    @Mapping(target = "id", ignore = true)
    Acteur acteurDtoToActeur(ActeurDTO acteurDTO);
}
