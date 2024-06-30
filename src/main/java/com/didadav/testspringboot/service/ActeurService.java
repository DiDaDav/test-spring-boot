package com.didadav.testspringboot.service;

import com.didadav.testspringboot.entity.Acteur;
import com.didadav.testspringboot.repository.ActeurRepository;
import com.didadav.testspringboot.utils.exception.ActeurException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActeurService {

    @Autowired
    private ActeurRepository acteurRepository;

    /**
     * Search for the given actor by name and surname.
     * If found, returns the actor with its id.
     * Else, create the actor in the database.
     *
     * @param acteur The actor to search for
     * @return The actor as instantiated in the database
     */
    public Acteur addActeurIfNotExists(Acteur acteur) throws ActeurException {
        if (!acteurRepository.existsByNomAndPrenom(acteur.getNom(), acteur.getPrenom()))
            return acteurRepository.save(acteur);
        else {
            Optional<Acteur> a = acteurRepository.findByNomAndPrenom(acteur.getNom(), acteur.getPrenom());
            if (a.isPresent()) return a.get();
        }
        throw new ActeurException("Something went wrong while operating actor " + acteur.getNom() + " " + acteur.getPrenom());
    }
}
