package org.example.entities;

import org.example.builders.ElementDeModuleBuilder;
import org.example.builders.FiliereBuilder;

import java.util.List;

import java.util.List;
import java.util.Objects;

public class ElementDeModule {
    private Long id;
    private Module module;
    private Filiere filiere;
    private String nom;
    private double coefficient;
    private List<ModaliteEvaluation> modalitesEvaluation;

    public ElementDeModule() {}

    public ElementDeModule(Long id, Module module, String nom, double coefficient, List<ModaliteEvaluation> modalitesEvaluation,Filiere filiere) {
        this.id = id;
        this.module = module;
        this.nom = nom;
        this.coefficient = coefficient;
        this.modalitesEvaluation = modalitesEvaluation;
        this.filiere=filiere;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public List<ModaliteEvaluation> getModalitesEvaluation() {
        return modalitesEvaluation;
    }

    public void setModalitesEvaluation(List<ModaliteEvaluation> modalitesEvaluation) {
        this.modalitesEvaluation = modalitesEvaluation;
    }

    @Override
    public String toString() {
        return "ElementDeModule{" +
                "id=" + id +
                ", module=" + module +
                ", nom='" + nom + '\'' +
                ", coefficient=" + coefficient +
                ", modalitesEvaluation=" + modalitesEvaluation +
                ", filiere=" + filiere +
                '}';
    }

    // equals and hashCode Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementDeModule that = (ElementDeModule) o;
        return id == that.id &&
                Double.compare(that.coefficient, coefficient) == 0 &&
                Objects.equals(module, that.module) &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(modalitesEvaluation, that.modalitesEvaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, module, nom, coefficient, modalitesEvaluation);
    }
    public static ElementDeModuleBuilder builder() {
        return new ElementDeModuleBuilder();
    }

}
