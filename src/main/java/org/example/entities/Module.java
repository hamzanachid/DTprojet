package org.example.entities;

import org.example.entities.builders.ModuleBuilder;
import org.example.utils.Semestre;

import java.util.List;

import java.util.Objects;

public class Module {
    private Long id;
    private String code;
    private String nom;
    private Filiere filiere;
    private Semestre semestre;
    private List<ElementDeModule> elementsDeModule;

    // Default Constructor
    public Module(long l, String number, String javaModule, Semestre s5, Object o) {}

    // Parameterized Constructor
    public Module(Long id, String code, String nom, Filiere filiere, Semestre semestre, List<ElementDeModule> elementsDeModule) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.filiere = filiere;
        this.semestre = semestre;
        this.elementsDeModule = elementsDeModule;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public List<ElementDeModule> getElementsDeModule() {
        return elementsDeModule;
    }

    public void setElementsDeModule(List<ElementDeModule> elementsDeModule) {
        this.elementsDeModule = elementsDeModule;
    }

    // toString Method
    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", filiere=" + filiere +
                ", semestre=" + semestre +
                ", elementsDeModule=" + elementsDeModule +
                '}';
    }

    // equals and hashCode Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return id == module.id &&
                Objects.equals(code, module.code) &&
                Objects.equals(nom, module.nom) &&
                Objects.equals(filiere, module.filiere) &&
                Objects.equals(semestre, module.semestre) &&
                Objects.equals(elementsDeModule, module.elementsDeModule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, nom, filiere, semestre, elementsDeModule);
    }
    public static ModuleBuilder builder() {
        return new ModuleBuilder();
    }


}
