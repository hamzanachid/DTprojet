package org.example.services.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.EtudiantDao;
import org.example.dao.impl.EtudiantDaoImpl;
import org.example.entities.Etudiant;
import org.example.services.EtudiantService;
import java.sql.SQLException;
import java.util.List;

public class EtudiantServiceImpl implements EtudiantService {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();
    public final static EtudiantService instance = new EtudiantServiceImpl();
    private final EtudiantDao etudiantDao = EtudiantDaoImpl.instance;

    private EtudiantServiceImpl() {
    }

    @Override
    public Etudiant create(Etudiant etudiant) {
        try {
            return etudiantDao.create(etudiant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Etudiant etudiant, Etudiant newEtudiant) {
        try {
            etudiantDao.update(etudiant, newEtudiant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        etudiantDao.delete(id);
    }

    @Override
    public Etudiant getById(Long id) {
        return etudiantDao.findById(id);
    }

    @Override
    public List<Etudiant> getByFiliere(Long filiereId) {
        return etudiantDao.findByFiliere(filiereId);
    }
}
