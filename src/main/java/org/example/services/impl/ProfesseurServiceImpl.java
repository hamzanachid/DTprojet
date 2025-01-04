package org.example.services.impl;

import org.example.dao.ProfesseurDao;
import org.example.entities.Professeur;
import org.example.services.ProfesseurService;
import java.util.List;
import java.util.Optional;

public class ProfesseurServiceImpl implements ProfesseurService {
    private static ProfesseurService instance;
    private final ProfesseurDao professeurDao;

    public static ProfesseurService getInstance(ProfesseurDao professeurDao) {
        if (instance == null)
            instance = new ProfesseurServiceImpl(professeurDao);
        return instance;
    }

    private ProfesseurServiceImpl(ProfesseurDao professeurDao) {
        this.professeurDao = professeurDao;
    }

    @Override
    public Professeur create(Professeur professeur) {
        return professeurDao.create(professeur);
    }

    @Override
    public Optional<Professeur> findById(Long id) {
        return professeurDao.findById(id);
    }

    @Override
    public Optional<Professeur> findByUserId(Long utilisateurId) {
        return professeurDao.findByUserId(utilisateurId);
    }

    @Override
    public List<Professeur> findAll() {
        return professeurDao.findAll();
    }

    @Override
    public void update(Professeur professeur) {
        professeurDao.update(professeur);
    }

    @Override
    public void delete(Long id) {
        professeurDao.delete(id);
    }
}