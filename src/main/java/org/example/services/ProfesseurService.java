package org.example.services;

import org.example.entities.Professeur;
import java.util.List;
import java.util.Optional;

public interface ProfesseurService {
    Professeur create(Professeur professeur);
    Optional<Professeur> findById(Long id);
    Optional<Professeur> findByCode(String code);
    Optional<Professeur> findByUserId(Long utilisateurId);
    List<Professeur> findAll();
    void update(Professeur professeur);
    void delete(Long id);
}