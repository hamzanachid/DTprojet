package org.example.entities;

import org.example.builders.UtilisateurBuilder;
import org.example.enums.EnumRole;

import java.util.Objects;

public class Utilisateur {
    private Long id;
    private String login;
    private String motDePasse;
    private EnumRole role;

    public Utilisateur() {}

    public Utilisateur(Long id, String login, String motDePasse, EnumRole role) {
        this.id = id;
        this.login = login;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilisateur)) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(motDePasse, that.motDePasse) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, motDePasse, role);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", role=" + role +
                '}';
    }

    public static UtilisateurBuilder builder() {
        return new UtilisateurBuilder();
    }
}