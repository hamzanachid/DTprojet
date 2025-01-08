package org.example.entities;

import org.example.builders.ProfesseurBuilder;

import java.util.List;

public class Professeur {
    private Long id;
    private String nom;
    private String prenom;
    private String specialite;
    private String code;
    private List<ElementDeModule> elementsDeModule;
    private Utilisateur utilisateur;

    public Professeur(Long id, String nom, String prenom, String specialite, String code, List<ElementDeModule> elementsDeModule, Utilisateur utilisateur) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.code = code;
        this.elementsDeModule = elementsDeModule;
        this.utilisateur = utilisateur;
    }

    public Professeur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public String getProfessorInfo() {
        return "Professeur { " +
          "id = " + id +
          ", prenom = '" + prenom + '\'' +
          ", nom = '" + nom + '\'' +
          ", specialite = '" + specialite + '\'' +
          ", code = '" + code + '\'' +
          ", login = '" + utilisateur.getLogin() + '\'' +
          " }";
    }

    @Override
    public String toString() {
        return "Professeur{" +
          "id=" + id +
          ", nom='" + nom + '\'' +
          ", prenom='" + prenom + '\'' +
          ", specialite='" + specialite + '\'' +
          ", code='" + code + '\'' +
          ", elementsDeModule=" + elementsDeModule +
          ", utilisateur=" + utilisateur +
          '}';
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public static ProfesseurBuilder builder() {
        return new ProfesseurBuilder();
    }
}