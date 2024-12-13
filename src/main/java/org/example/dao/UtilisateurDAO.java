package org.example.dao;

import org.example.entities.Utilisateur;

public interface UtilisateurDAO {
    void save(Utilisateur utilisateur);
    Utilisateur findById(Long id);
    Utilisateur findByUsername(String username);
    void update(Utilisateur utilisateur);
    void delete(Long id);
    Utilisateur login(String username, String password);
    void signUp(Utilisateur utilisateur);
}
