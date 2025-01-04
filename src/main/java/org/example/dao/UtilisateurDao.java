package org.example.dao;


import org.example.entities.Utilisateur;
import org.example.enums.EnumRole;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDao {
    Utilisateur create(Utilisateur utilisateur);
    Optional<Utilisateur> findById(Long id);
    Optional<Utilisateur> findByLogin(String login);
    List<Utilisateur> findAll();
    List<Utilisateur> findByRole(EnumRole role);
    List<Utilisateur> findByNom(String nom);
    Utilisateur update(Utilisateur utilisateur);
    boolean updateMotDePasse(Long id, String newMotDePasse);
    boolean delete(Long id);
    Optional<Utilisateur> authenticate(String login, String motDePasse);
    boolean existsByLogin(String login);
    List<Utilisateur> saveAll(List<Utilisateur> utilisateurs);
}