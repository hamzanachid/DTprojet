package org.example.dao;

import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;

import java.util.List;

public interface FiliereDao {
    Filiere create(Filiere filiere);
    Filiere findById(Long id);
    void update(Filiere filiere);
    void delete(Long id);
    List<Filiere> findAll();
}
