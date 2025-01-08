package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.Professeur;
import org.example.entities.Utilisateur;

import java.util.List;

public class ProfesseurBuilder {
    private Long id;
    private String nom;
    private String prenom;
    private String specialite;
    private String code;
    private List<ElementDeModule> elementsDeModule;
    private Utilisateur utilisateur;

    public ProfesseurBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProfesseurBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public ProfesseurBuilder setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public ProfesseurBuilder setSpecialite(String specialite) {
        this.specialite = specialite;
        return this;
    }

    public ProfesseurBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public ProfesseurBuilder setElementsDeModule(List<ElementDeModule> elementsDeModule) {
        this.elementsDeModule = elementsDeModule;
        return this;
    }

    public ProfesseurBuilder setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public Professeur build() {
        return new Professeur(id, nom, prenom, specialite, code, elementsDeModule, utilisateur);
    }
}