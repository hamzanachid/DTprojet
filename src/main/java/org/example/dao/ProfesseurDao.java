package org.example.dao;

import org.example.entities.Professeur;

import java.util.List;
import java.util.Optional;

public interface ProfesseurDao {
    Professeur create(Professeur professeur);
    Optional<Professeur> findById(Long id);
    Optional<Professeur> findByUserId(Long utilisateurId);
    List<Professeur> findAll();
    void update(Professeur professeur);
    void delete(Long id);
}
