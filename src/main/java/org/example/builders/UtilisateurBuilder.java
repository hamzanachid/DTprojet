package org.example.builders;

import org.example.entities.Utilisateur;

public class UtilisateurBuilder {

    private Long id;
    private String username;
    private String password;
    private String role;

    // Setter methods for each field
    public UtilisateurBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UtilisateurBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UtilisateurBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UtilisateurBuilder setRole(String role) {
        this.role = role;
        return this;
    }

    public Utilisateur build() {
        return new Utilisateur(id, username, password, role);
    }
}
