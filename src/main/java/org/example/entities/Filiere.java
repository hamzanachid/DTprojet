package org.example.entities;

import org.example.builders.FiliereBuilder;

import java.util.List;
import java.util.Objects;

public class Filiere {
    private int id;
    private String nom;
    private String code;
    private List<ElementDeModule> elementDeModules;


    public Filiere() {}

    // Parameterized Constructor
    public Filiere(int id, String nom, List<ElementDeModule> elementDeModules) {
        this.id = id;
        this.nom = nom;
        this.elementDeModules = elementDeModules;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<ElementDeModule> getElementDeModules() {
        return elementDeModules;
    }

    public void setElementDeModules(List<ElementDeModule> elementDeModules) {
        this.elementDeModules = elementDeModules;
    }

    // toString Method
    @Override
    public String toString() {
        return "Filiere{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", elementDeModules=" + elementDeModules +
                '}';
    }

    // equals and hashCode Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filiere filiere = (Filiere) o;
        return id == filiere.id &&
                Objects.equals(nom, filiere.nom) &&
                Objects.equals(elementDeModules, filiere.elementDeModules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, elementDeModules);
    }
    public static FiliereBuilder builder() {
        return new FiliereBuilder();
    }

}
