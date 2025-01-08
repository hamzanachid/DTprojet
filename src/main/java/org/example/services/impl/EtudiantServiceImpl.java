package org.example.services.impl;


import org.example.dao.EtudiantDao;
import org.example.entities.Etudiant;
import org.example.services.EtudiantService;
import java.util.List;

import static org.example.utils.CheckAccess.checkUserAccess;

public class EtudiantServiceImpl implements EtudiantService {
    public static EtudiantService instance;
    private final EtudiantDao etudiantDao;

    private EtudiantServiceImpl(EtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }
    public static EtudiantService getInstance(EtudiantDao etudiantDao) {
        if(instance == null) {
            instance = new EtudiantServiceImpl(etudiantDao);
        }
        return instance;
    }
     @Override
    public Etudiant create(Etudiant etudiant) {
        checkUserAccess();

         return etudiantDao.create(etudiant);
     }
    @Override
    public boolean delete(Long id) {
        return etudiantDao.delete(id);
    }

    @Override
    public List<Etudiant> findEtudiantByElement(Long element_id) {
        return etudiantDao.findEtudiantByElement(element_id);
    }

    @Override
    public void update(Etudiant etudiant, Etudiant newEtudiant) {

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
