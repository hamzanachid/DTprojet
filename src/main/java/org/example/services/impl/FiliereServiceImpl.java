package org.example.services.impl;

import org.example.dao.FiliereDao;
import org.example.dao.impl.FiliereDaoImpl;
import org.example.entities.Filiere;
import org.example.services.FiliereService;

import java.util.List;

public class FiliereServiceImpl implements FiliereService {
    private static FiliereDao filiereDao = FiliereDaoImpl.instance;
    public static final FiliereService instance = new FiliereServiceImpl();

    private FiliereServiceImpl() {
    }

    @Override
    public Filiere create(Filiere filiere) {
        if (filiere == null) {
            throw new IllegalArgumentException("Filiere cannot be null");
        }
        if (filiere.getNom() == null || filiere.getNom().isEmpty()) {
            throw new IllegalArgumentException("Filiere name cannot be null or empty");
        }
        if (filiere.getCode() == null || filiere.getCode().isEmpty()) {
            throw new IllegalArgumentException("Filiere code cannot be null or empty");
        }
        return filiereDao.create(filiere);
    }

    @Override
    public void update(Filiere filiere) {
        if (filiere == null || filiere.getId() <= 0) {
            throw new IllegalArgumentException("Invalid Filiere or ID");
        }
        filiereDao.update(filiere);
    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Filiere ID");
        }
        filiereDao.delete(id);
    }

    @Override
    public Filiere getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Filiere ID");
        }
        Filiere filiere = filiereDao.findById(id);
        if (filiere == null) {
            throw new IllegalStateException("Filiere not found for ID: " + id);
        }
        return filiere;
    }

    @Override
    public List<Filiere> getAll() {
        List<Filiere> filieres = filiereDao.findAll();
        if (filieres == null || filieres.isEmpty()) {
            throw new IllegalStateException("No filieres found in the database");
        }
        return filieres;
    }
}
