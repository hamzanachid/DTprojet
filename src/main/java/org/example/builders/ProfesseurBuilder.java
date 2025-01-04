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

    public ProfesseurBuilder withId(Long id) {
        this.id = id;
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
        return new Professeur(id,  nom, prenom, specialite, code, elementsDeModule, utilisateur);
    }
}