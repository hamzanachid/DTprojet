package org.example.entities;

import org.example.builders.FiliereBuilder;

import java.util.List;
import java.util.Objects;

public class Filiere {
    private Long id;
    private String nom;
    private String code;
    private List<ElementDeModule> elementDeModules;

    public Filiere() {
    }

    @Override
    public String toString() {
        return "Filiere{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filiere)) return false;
        Filiere filiere = (Filiere) o;
        return id == filiere.id && Objects.equals(nom, filiere.nom) && Objects.equals(code, filiere.code) && Objects.equals(elementDeModules, filiere.elementDeModules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, code, elementDeModules);
    }

    public Filiere(Long id, String nom, String code, List<ElementDeModule> elementDeModules) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.elementDeModules = elementDeModules;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ElementDeModule> getElementDeModules() {
        return elementDeModules;
    }

    public void setElementDeModules(List<ElementDeModule> elementDeModules) {
        this.elementDeModules = elementDeModules;
    }

    public static FiliereBuilder builder() {
        return new FiliereBuilder();
    }

}
