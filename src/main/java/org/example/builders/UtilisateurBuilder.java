package org.example.builders;

import org.example.entities.Utilisateur;
import org.example.enums.Role;

public class UtilisateurBuilder {
    private Long id;
    private String login;
    private String motDePasse;
    private Role role;

    public UtilisateurBuilder withId(Long id) {
        this.id = id;
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

    public UtilisateurBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public Utilisateur build() {
        return new Utilisateur(id, login, motDePasse, role);
    }
}