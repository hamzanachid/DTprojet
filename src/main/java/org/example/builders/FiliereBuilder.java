package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;

import java.util.List;

public  class FiliereBuilder {
    private Long id;
    private String nom;
    private String code;
    private List<ElementDeModule> elementDeModules;

    public FiliereBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public FiliereBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public FiliereBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public FiliereBuilder setElementDeModules(List<ElementDeModule> elementDeModules) {
        this.elementDeModules = elementDeModules;
        return this;
    }

    public Filiere build() {
        return new Filiere(id, nom,code, elementDeModules);
    }
}
