package org.example.services;

import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;

import java.util.List;

public interface FiliereService {
    Filiere create(Filiere filiere);
    void update(Filiere filiere);
    void delete(Long id);
    Filiere getById(Long id);
    List<Filiere> getAll();
    List<ElementDeModule> getElements(Long filiereId);
}
