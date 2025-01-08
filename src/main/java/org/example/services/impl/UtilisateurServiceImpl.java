package org.example.services.impl;

import org.example.dao.UtilisateurDAO;
import org.example.dao.impl.UtilisateurDAOImpl;
import org.example.entities.Utilisateur;
import org.example.utils.EnumRole;
import org.example.services.UtilisateurService;
import org.example.entities.builders.UtilisateurBuilder;
import java.util.List;
import java.util.Optional;

public class UtilisateurServiceImpl implements UtilisateurService {
    private static final UtilisateurDAO utilisateurDAO = UtilisateurDAOImpl.instance;
    public static final UtilisateurService instance = new UtilisateurServiceImpl();

    private UtilisateurServiceImpl() {}

    @Override
    public Utilisateur createUtilisateur(String nom, String prenom, String login, String motDePasse, EnumRole role) {
        Utilisateur utilisateur = new UtilisateurBuilder()
                .withNom(nom)
                .withPrenom(prenom)
                .withLogin(login)
                .withMotDePasse(motDePasse)
                .withRole(role)
                .build();

        return utilisateurDAO.create(utilisateur);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurDAO.findById(id);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurByLogin(String login) {
        return utilisateurDAO.findByLogin(login);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.findAll();
    }

    @Override
    public List<Utilisateur> getUtilisateursByRole(EnumRole role) {
        return utilisateurDAO.findByRole(role);
    }

    @Override
    public List<Utilisateur> getUtilisateursByNom(String nom) {
        return utilisateurDAO.findByNom(nom);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurDAO.update(utilisateur);
    }

    @Override
    public boolean updateMotDePasse(Long id, String oldMotDePasse, String newMotDePasse) {
        return false;
    }

    @Override
    public boolean updateMotDePasse(Long id, String newMotDePasse) {
        return utilisateurDAO.updateMotDePasse(id, newMotDePasse);
    }

    @Override
    public boolean deleteUtilisateur(Long id) {
        return utilisateurDAO.delete(id);
    }

    @Override
    public Optional<Utilisateur> authenticateUtilisateur(String login, String motDePasse) {
        return utilisateurDAO.authenticate(login, motDePasse);
    }

    @Override
    public Optional<Utilisateur> loginUtilisateur(String login, String motDePasse) {
        return utilisateurDAO.authenticate(login, motDePasse);
    }

    @Override
    public boolean isLoginAvailable(String login) {
        return !utilisateurDAO.existsByLogin(login);
    }

    @Override
    public boolean validateMotDePasse(String motDePasse) {
        return motDePasse != null && motDePasse.length() >= 8;
    }

    @Override
    public List<Utilisateur> saveAllUtilisateurs(List<Utilisateur> utilisateurs) {
        return utilisateurDAO.saveAll(utilisateurs);
    }

    @Override
    public Utilisateur signUpUtilisateur(String nom, String prenom, String login, String motDePasse, EnumRole role) {
        Utilisateur utilisateur = new UtilisateurBuilder()
                .withNom(nom)
                .withPrenom(prenom)
                .withLogin(login)
                .withMotDePasse(motDePasse)
                .withRole(role)
                .build();

        return utilisateurDAO.create(utilisateur);
    }
}
