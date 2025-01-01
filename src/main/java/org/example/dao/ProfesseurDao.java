package org.example.dao;

import org.example.entities.Professeur;
import org.example.enums.Role;

import java.util.List;
import java.util.Optional;

public interface ProfesseurDao {
  Professeur create(Professeur professeur);

  Optional<Professeur> findById(Long id);

  Optional<Professeur> findByLogin(String login);

  List<Professeur> findAll();

  List<Professeur> findByRole(Role role);

  List<Professeur> findByNom(String nom);

  Professeur update(Professeur professeur);

  boolean updateMotDePasse(Long id, String newMotDePasse);

  boolean delete(Long id);

  Optional<Professeur> authenticate(String login, String motDePasse);

  boolean existsByLogin(String login);

  List<Professeur> saveAll(List<Professeur> professeurs);
}
