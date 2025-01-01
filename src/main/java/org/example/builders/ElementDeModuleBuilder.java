package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.ModaliteEvaluation;
import org.example.entities.Module;

import java.util.List;

public   class ElementDeModuleBuilder {
    private int id;
    private Module module;
    private String nom;
    private double coefficient;
    private List<ModaliteEvaluation> modalitesEvaluation;

    public ElementDeModuleBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ElementDeModuleBuilder setModule(Module module) {
        this.module = module;
        return this;
    }

    public ElementDeModuleBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public ElementDeModuleBuilder setCoefficient(double coefficient) {
        this.coefficient = coefficient;
        return this;
    }

    public ElementDeModuleBuilder setModalitesEvaluation(List<ModaliteEvaluation> modalitesEvaluation) {
        this.modalitesEvaluation = modalitesEvaluation;
        return this;
    }

    public ElementDeModule build() {
        return new ElementDeModule(id, module, nom, coefficient, modalitesEvaluation);
    }
}