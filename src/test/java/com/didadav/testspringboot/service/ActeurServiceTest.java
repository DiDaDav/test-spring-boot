package com.didadav.testspringboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.didadav.testspringboot.entity.Acteur;
import com.didadav.testspringboot.utils.exception.ActeurException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ActeurServiceTest {

    @Autowired
    ActeurService acteurService;

    @Test
    @DisplayName("add acteur when not exist")
    void addActeurWhenNotExists() throws ActeurException {
        Acteur a = acteurService.addActeurIfNotExists(new Acteur("Cena", "John"));
        assertEquals(a.getNom(), "Cena");
        assertEquals(a.getPrenom(), "John");
        assertNotNull(a.getId());
    }

    @Test
    @DisplayName("don't add acteur if already exists")
    void dontAddActeurIfExists() throws ActeurException {
        Acteur first = acteurService.addActeurIfNotExists(new Acteur("Bell", "Mary"));
        Acteur a = acteurService.addActeurIfNotExists(new Acteur("Bell", "Mary"));
        assertEquals(a.getId(), first.getId());
    }
}
