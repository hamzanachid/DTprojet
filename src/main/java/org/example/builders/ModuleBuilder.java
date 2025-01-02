package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.enums.Semestre;

import java.util.List;

public  class ModuleBuilder {
    private Long id;
    private String code;
    private String nom;
    private Filiere filiere;
    private Semestre semestre;
    private List<ElementDeModule> elementsDeModule;

    public ModuleBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ModuleBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public ModuleBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public ModuleBuilder setFiliere(Filiere filiere) {
        this.filiere = filiere;
        return this;
    }

    public ModuleBuilder setSemestre(Semestre semestre) {
        this.semestre = semestre;
        return this;
    }

    public ModuleBuilder setElementsDeModule(List<ElementDeModule> elementsDeModule) {
        this.elementsDeModule = elementsDeModule;
        return this;
    }

    public Module build() {
        return new Module(id, code, nom, filiere, semestre, elementsDeModule);
    }
}
