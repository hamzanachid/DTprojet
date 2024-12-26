package org.example.services;

import org.example.entities.Utilisateur;
import org.example.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    // Create operations
    Utilisateur createUtilisateur(String nom, String prenom, String login, String motDePasse, Role role);

    // Read operations
    Optional<Utilisateur> getUtilisateurById(Long id);
    Optional<Utilisateur> getUtilisateurByLogin(String login);
    List<Utilisateur> getAllUtilisateurs();
    List<Utilisateur> getUtilisateursByRole(Role role);

    List<Utilisateur> getUtilisateursByNom(String nom);

    // Update operations
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    boolean updateMotDePasse(Long id, String oldMotDePasse, String newMotDePasse);

    boolean updateMotDePasse(Long id, String newMotDePasse);

    // Delete operations
    boolean deleteUtilisateur(Long id);

    // Authentication operations
    boolean authenticateUtilisateur(String login, String motDePasse);
    Optional<Utilisateur> loginUtilisateur(String login, String motDePasse);

    // Batch operation
    List<Utilisateur> saveAllUtilisateurs(List<Utilisateur> utilisateurs);

    Utilisateur signUpUtilisateur(String nom, String prenom, String login, String motDePasse, Role role);

    // Validation operations
    boolean isLoginAvailable(String login);
    boolean validateMotDePasse(String motDePasse);
}