package com.didadav.testspringboot.repository;

import com.didadav.testspringboot.entity.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActeurRepository extends JpaRepository<Acteur, Long> {
    boolean existsByNomAndPrenom(String nom, String prenom);
    Optional<Acteur> findByNomAndPrenom(String nom, String prenom);
}
