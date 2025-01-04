package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.entities.ModaliteEvaluation;
import org.example.entities.Module;

import java.util.List;

public class ElementDeModuleBuilder {
    private Long id;
    private Module module;
    private Filiere filiere;
    private String nom;
    private double coefficient;
    private List<ModaliteEvaluation> modalitesEvaluation;

    public ElementDeModuleBuilder setId(Long id) {
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
    public ElementDeModuleBuilder setFiliere(Filiere filiere) {
        this.filiere = filiere;
        return this;
    }

    public ElementDeModule build() {
        return new ElementDeModule(id, module, nom, coefficient, modalitesEvaluation,filiere);
    }
}