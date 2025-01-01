package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;

import java.util.List;

public  class FiliereBuilder {
    private int id;
    private String nom;
    private List<ElementDeModule> elementDeModules;

    public FiliereBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public FiliereBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public FiliereBuilder setElementDeModules(List<ElementDeModule> elementDeModules) {
        this.elementDeModules = elementDeModules;
        return this;
    }

    public Filiere build() {
        return new Filiere(id, nom, elementDeModules);
    }
}
