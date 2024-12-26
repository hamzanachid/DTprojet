package org.example.services.impl;

import org.example.dao.UtilisateurDAO;
import org.example.entities.Utilisateur;
import org.example.enums.Role;
import org.example.services.UtilisateurService;
import org.example.builders.UtilisateurBuilder;

import java.util.List;
import java.util.Optional;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }
 
    @Override
    public Utilisateur createUtilisateur(String nom, String prenom, String login, String motDePasse, Role role) {
        // Using the builder to create a new Utilisateur
        Utilisateur utilisateur = new UtilisateurBuilder()
                .withNom(nom)
                .withPrenom(prenom)
                .withLogin(login)
                .withMotDePasse(motDePasse)
                .withRole(role)
                .build();

        return utilisateurDAO.create(utilisateur); // Persist utilisateur
    }

    // Read operations
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
    public List<Utilisateur> getUtilisateursByRole(Role role) {
        return utilisateurDAO.findByRole(role);
    }

    @Override
    public List<Utilisateur> getUtilisateursByNom(String nom) {
        return utilisateurDAO.findByNom(nom);
    }

    // Update operations
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

    // Delete operations
    @Override
    public boolean deleteUtilisateur(Long id) {
        return utilisateurDAO.delete(id);
    }

    // Authentication operations
    @Override
    public boolean authenticateUtilisateur(String login, String motDePasse) {
        Optional<Utilisateur> utilisateur = utilisateurDAO.authenticate(login, motDePasse);
        return utilisateur.isPresent();
    }

    @Override
    public Optional<Utilisateur> loginUtilisateur(String login, String motDePasse) {
        return utilisateurDAO.authenticate(login, motDePasse);
    }

    // Additional helper methods
    @Override
    public boolean isLoginAvailable(String login) {
        return !utilisateurDAO.existsByLogin(login);
    }

    @Override
    public boolean validateMotDePasse(String motDePasse) {
        // Simple password validation (for example, check length)
        return motDePasse != null && motDePasse.length() >= 8;
    }

    // Batch operation
    @Override
    public List<Utilisateur> saveAllUtilisateurs(List<Utilisateur> utilisateurs) {
        return utilisateurDAO.saveAll(utilisateurs);
    }

    // Sign up operation using the builder
    @Override
    public Utilisateur signUpUtilisateur(String nom, String prenom, String login, String motDePasse, Role role) {
        // Using the builder to create a new Utilisateur
        Utilisateur utilisateur = new UtilisateurBuilder()
                .withNom(nom)
                .withPrenom(prenom)
                .withLogin(login)
                .withMotDePasse(motDePasse)
                .withRole(role)
                .build();

        return utilisateurDAO.create(utilisateur); // Persist the utilisateur
    }
}
