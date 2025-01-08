package org.example.services;

import org.example.entities.Utilisateur;
import org.example.utils.EnumRole;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    Utilisateur createUtilisateur(String login, String motDePasse, EnumRole role);

    Optional<Utilisateur> getUtilisateurById(Long id);
    Optional<Utilisateur> getUtilisateurByLogin(String login);
    List<Utilisateur> getAllUtilisateurs();
    List<Utilisateur> getUtilisateursByRole(EnumRole role);
    List<Utilisateur> getUtilisateursByNom(String nom);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    boolean updateMotDePasse(Long id, String oldMotDePasse, String newMotDePasse);
    boolean updateMotDePasse(Long id, String newMotDePasse);
    boolean deleteUtilisateur(Long id);
    Optional<Utilisateur> authenticateUtilisateur(String login, String motDePasse);
    Optional<Utilisateur> loginUtilisateur(String login, String motDePasse);
    List<Utilisateur> saveAllUtilisateurs(List<Utilisateur> utilisateurs);
    boolean isLoginAvailable(String login);
    boolean validateMotDePasse(String motDePasse);
}