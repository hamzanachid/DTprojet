package org.example.entities;

import org.example.enums.Role;

import java.util.List;

public class Professeur extends Utilisateur {
  private String nom;
  private String prenom;
  private String specialite;
  private String code;
  private List<ElementDeModule> elementsDeModule;

  @Override
  public String toString() {
    return "Professeur{" +
      "nom='" + nom + '\'' +
      ", prenom='" + prenom + '\'' +
      ", specialite='" + specialite + '\'' +
      ", code='" + code + '\'' +
      ", elementsDeModule=" + elementsDeModule +
      '}';
  }

  public Professeur(
    String nom,
    String prenom,
    String specialite,
    String code,
    List<ElementDeModule> elementsDeModule
  ) {
    this.nom = nom;
    this.prenom = prenom;
    this.specialite = specialite;
    this.code = code;
    this.elementsDeModule = elementsDeModule;
  }

  public Professeur(
    Long id,
    String login,
    String motDePasse,
    Role role,
    String nom,
    String prenom,
    String specialite,
    String code, List<ElementDeModule> elementsDeModule) {
    super(
      id,
      login,
      motDePasse,
      role
    );
    this.nom = nom;
    this.prenom = prenom;
    this.specialite = specialite;
    this.code = code;
    this.elementsDeModule = elementsDeModule;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getSpecialite() {
    return specialite;
  }

  public void setSpecialite(String specialite) {
    this.specialite = specialite;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<ElementDeModule> getElementsDeModule() {
    return elementsDeModule;
  }

  public void setElementsDeModule(List<ElementDeModule> elementsDeModule) {
    this.elementsDeModule = elementsDeModule;
  }
}