package org.example.services.impl;


import org.example.dao.EtudiantDao;
import org.example.entities.Etudiant;
import org.example.services.EtudiantService;
import org.example.validation.BasicValidationStrategy;
import org.example.validation.CompositeValidationStrategy;
import org.example.validation.ValidationStrategy;

import java.util.List;

import static org.example.utils.CheckAccess.checkUserAccess;

public class EtudiantServiceImpl implements EtudiantService {
    public static EtudiantService instance;
    private final EtudiantDao etudiantDao;
    private final ValidationStrategy validationStrategy;


    private EtudiantServiceImpl(EtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
        this.validationStrategy = new CompositeValidationStrategy(new BasicValidationStrategy(), new BasicValidationStrategy());
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
        checkUserAccess();
        return etudiantDao.create(etudiant);
    }

    @Override
    public boolean delete(Long id) {
        return etudiantDao.delete(id);
    }

    @Override
    public List<Etudiant> findEtudiantByElement(Long element_id) {
        System.out.println(element_id);
        return etudiantDao.findEtudiantByElement(element_id);
    }

    @Override
    public void update(Etudiant etudiant, Etudiant newEtudiant) {
        validationStrategy.validate(etudiant);
        checkUserAccess();
        etudiantDao.update(etudiant, newEtudiant);
    }

    @Override
    public Etudiant getById(Long id) {
        return etudiantDao.findById(id);
    }

    @Override
    public List<Etudiant> getByFiliere(Long filiereId) {
        return etudiantDao.findByFiliere(filiereId);
    }

    @Override
    public List<Etudiant> findAll() {
        return etudiantDao.findAll();
    }

    @Override
    public Etudiant getByMatricule(String matricule) {
        return etudiantDao.getByMatricule(matricule);
    }
}
