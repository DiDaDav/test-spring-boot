package com.didadav.testspringboot.service;

import com.didadav.testspringboot.entity.Film;
import com.didadav.testspringboot.repository.FilmRepository;
import com.didadav.testspringboot.utils.exception.ActeurException;
import com.didadav.testspringboot.utils.exception.FilmNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ActeurService acteurService;

    /**
     * Check for a film in the database based on an id.
     *
     * @param id The id of the film
     * @return The {@link  com.didadav.testspringboot.entity.Film} found in the database
     * @throws FilmNotFoundException when no film was found
     */
    public Film getFilmById(Long id) throws FilmNotFoundException {
        Optional<Film> foundFilm = filmRepository.findById(id);
        if (foundFilm.isPresent()) return foundFilm.get();
        else throw new FilmNotFoundException("The film with id " + id + " was not found");
    }

    /**
     * Save a new film to the database.
     * Add every new actors to the database (based on the combination of their name and surname) before saving the film.
     *
     * @param film The {@link  com.didadav.testspringboot.entity.Film} to add, bared the id
     * @return The newly created {@link  com.didadav.testspringboot.entity.Film} object
     */
    public Film createFilm(Film film) {
        film.setActeurs(film.getActeurs().stream().map((a) -> {
            try {
                return acteurService.addActeurIfNotExists(a);
            } catch (ActeurException e) {
                throw new RuntimeException(e);
            }
        }).toList());
        return filmRepository.save(film);
    }

    /**
     * Get the current number of film stored in the database
     * @return the number of film
     */
    public long getSize() {
        return filmRepository.count();
    }
}
