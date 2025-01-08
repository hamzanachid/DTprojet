package org.example.entities.builders;

import org.example.entities.Utilisateur;
import org.example.utils.EnumRole;

public class UtilisateurBuilder {
    private Long id;
    private String nom;
    private String prenom;
    private String login;
    private String motDePasse;
    private EnumRole role;

    public UtilisateurBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UtilisateurBuilder withNom(String nom) {
        this.nom = nom;
        return this;
    }

    public UtilisateurBuilder withPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public UtilisateurBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UtilisateurBuilder withMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
        return this;
    }

    public UtilisateurBuilder withRole(EnumRole role) {
        this.role = role;
        return this;
    }

    public Utilisateur build() {
        return new Utilisateur(id, nom, prenom, login, motDePasse, role);
    }
}