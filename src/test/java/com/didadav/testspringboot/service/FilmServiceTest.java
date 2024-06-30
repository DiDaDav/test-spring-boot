package com.didadav.testspringboot.service;

import com.didadav.testspringboot.entity.Acteur;
import com.didadav.testspringboot.entity.Film;
import com.didadav.testspringboot.utils.exception.FilmNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class FilmServiceTest {
    @Autowired
    FilmService filmService;

    @Test
    @DisplayName("get a film that exists")
    void getFilmExists() {
        try {
            Film film = filmService.getFilmById(1L);
            assertEquals(film.getTitre(), "Film 1");
        } catch (FilmNotFoundException e) {
            // no exception
        }
    }

    @Test
    @DisplayName("get a film that doesn't exists")
    void getFilmNotExists() {
        FilmNotFoundException e = assertThrows(
                FilmNotFoundException.class,
                () -> filmService.getFilmById(1000000L),
                "Expected film not to exists, but existed"
        );
        assertTrue(e.getMessage().contains("1000000"));
    }

    @Test
    @DisplayName("add a new film")
    void createFilm() {
        Film f = filmService.createFilm(new Film("Hello World", "Every devs first step", List.of(new Acteur("Self", "My"))));
        assertEquals(f.getId(), filmService.getSize());
        assertEquals(f.getTitre(), "Hello World");
        assertEquals(f.getDescription(), "Every devs first step");
        assertNotNull(f.getActeurs());
    }
}
