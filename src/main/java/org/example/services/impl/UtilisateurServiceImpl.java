package org.example.services.impl;

import org.example.dao.UtilisateurDAO;
import org.example.entities.Utilisateur;
import org.example.services.UtilisateurService;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public void saveUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.save(utilisateur);
    }

    @Override
    public Utilisateur getUtilisateur(Long id) {
        return utilisateurDAO.findById(id);
    }

    @Override
    public Utilisateur login(String username, String password) {
        return utilisateurDAO.login(username, password);
    }

    @Override
    public void signUp(Utilisateur utilisateur) {
        utilisateurDAO.signUp(utilisateur);
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        utilisateurDAO.delete(id);
    }
}
