package org.example.services;

import org.example.entities.Etudiant;
import java.util.List;

public interface EtudiantService {
    Etudiant create(Etudiant etudiant);
    void update(Etudiant etudiant, Etudiant newEtudiant);
    void delete(Long id);
    Etudiant getById(Long id);
    List<Etudiant> getByFiliere(Long filiereId);
    List<Etudiant> findAll();
    Etudiant getByMatricule(String matricule);
}

