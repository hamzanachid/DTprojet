package org.example.services.impl;

import org.example.dao.EtudiantDao;
import org.example.entities.Etudiant;
import org.example.services.EtudiantService;
import org.example.validation.BasicValidationStrategy;
import org.example.validation.CompositeValidationStrategy;
import org.example.validation.MatriculeValidationStrategy;
import org.example.validation.ValidationStrategy;
import java.util.List;

public class EtudiantServiceImpl implements EtudiantService {
    public static EtudiantService instance;
    private final EtudiantDao etudiantDao;
    private final ValidationStrategy validationStrategy;

    private EtudiantServiceImpl(EtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
        this.validationStrategy = new CompositeValidationStrategy(
                new BasicValidationStrategy(),
                new MatriculeValidationStrategy()
        );
    }

    public static EtudiantService getInstance(EtudiantDao etudiantDao) {
        if(instance == null) {
            instance = new EtudiantServiceImpl(etudiantDao);
        }
        return instance;
    }

    @Override
    public Etudiant create(Etudiant etudiant) {
        validationStrategy.validate(etudiant);
        if (etudiantDao.getByMatricule(etudiant.getMatricule()) != null) {
            throw new IllegalArgumentException("matricule already exists");
        }
        return etudiantDao.create(etudiant);
    }

    @Override
    public void update(Etudiant oldStudent, Etudiant newStudent) {
        validationStrategy.validate(newStudent);
        var existing = etudiantDao.getByMatricule(newStudent.getMatricule());
        if (existing != null && !existing.getId().equals(oldStudent.getId())) {
            throw new IllegalArgumentException("matricule already exists");
        }
        etudiantDao.update(oldStudent, newStudent);
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }
        return etudiantDao.delete(id);
    }

    @Override
    public Etudiant getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }
        return etudiantDao.findById(id);
    }

    @Override
    public List<Etudiant> getByFiliere(Long filiereId) {
        if (filiereId == null) {
            throw new IllegalArgumentException("filiere id is required");
        }
        return etudiantDao.findByFiliere(filiereId);
    }

    @Override
    public List<Etudiant> findEtudiantByElement(Long elementId) {
        if (elementId == null) {
            throw new IllegalArgumentException("element id is required");
        }
        return etudiantDao.findEtudiantByElement(elementId);
    }

    @Override
    public List<Etudiant> findAll() {
        return etudiantDao.findAll();
    }

    @Override
    public Etudiant getByMatricule(String matricule) {
        if (matricule.isEmpty()) {
            throw new IllegalArgumentException("matricule is required");
        }
        return etudiantDao.getByMatricule(matricule);
    }
}