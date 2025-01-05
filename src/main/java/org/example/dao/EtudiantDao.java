package org.example.dao;

import org.example.entities.Etudiant;

import java.sql.SQLException;
import java.util.List;

public interface EtudiantDao {
    Etudiant create(Etudiant etudiant);
    boolean update(Etudiant etudiant, Etudiant newEtudiant);
    boolean delete(Long id);
    Etudiant findById(Long id);
    public List<Etudiant> findEtudiantByElement(Long element_id);
    List<Etudiant> findByFiliere(Long filiereId);
    List<Etudiant> findAll();
    Etudiant getByMatricule(String matricule);
}