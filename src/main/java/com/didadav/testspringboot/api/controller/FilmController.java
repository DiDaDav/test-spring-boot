package com.didadav.testspringboot.api.controller;

import com.didadav.testspringboot.api.mapper.FilmMapper;
import com.didadav.testspringboot.entity.Film;
import com.didadav.testspringboot.service.FilmService;
import com.didadav.testspringboot.api.dto.FilmDTO;
import com.didadav.testspringboot.utils.exception.FilmNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/film")
public class FilmController {

    @Autowired
    FilmService filmService;
    @Autowired
    FilmMapper filmMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable("id") long id) {
        try {
            return ResponseEntity.status(200).body(filmService.getFilmById(id));
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(204).body(new Film());
        }
    }

    @PostMapping()
    public ResponseEntity<Film> addFilm(@RequestBody FilmDTO filmDTO) {
        try {
            return ResponseEntity.status(201).body(filmService.createFilm(filmMapper.filmDtoToFilm(filmDTO)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(204).body(new Film());
        }
    }
}
