package org.example.services;

import org.example.entities.Utilisateur;

public interface UtilisateurService {
    void saveUtilisateur(Utilisateur utilisateur);
    Utilisateur getUtilisateur(Long id);
    Utilisateur login(String username, String password);
    void signUp(Utilisateur utilisateur);
    void updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(Long id);
}
