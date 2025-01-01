package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.Professeur;
import org.example.enums.Role;

import java.util.List;

public class ProfesseurBuilder {
  private Long id;
  private String login;
  private String motDePasse;
  private Role role;
  private String nom;
  private String prenom;
  private String specialite;
  private String code;
  private List<ElementDeModule> elementsDeModule;

  public ProfesseurBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public ProfesseurBuilder withLogin(String login) {
    this.login = login;
    return this;
  }

  public ProfesseurBuilder withMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
    return this;
  }

  public ProfesseurBuilder withRole(Role role) {
    this.role = role;
    return this;
  }

  public ProfesseurBuilder withNom(String nom) {
    this.nom = nom;
    return this;
  }

  public ProfesseurBuilder withPrenom(String prenom) {
    this.prenom = prenom;
    return this;
  }

  public ProfesseurBuilder withSpecialite(String specialite) {
    this.specialite = specialite;
    return this;
  }

  public ProfesseurBuilder withCode(String code) {
    this.code = code;
    return this;
  }

  public ProfesseurBuilder withElementsDeModule(List<ElementDeModule> elementsDeModule) {
    this.elementsDeModule = elementsDeModule;
    return this;
  }

  public Professeur build() {
    return new Professeur(id, login, motDePasse, role, nom, prenom, specialite, code, elementsDeModule);
  }
}
