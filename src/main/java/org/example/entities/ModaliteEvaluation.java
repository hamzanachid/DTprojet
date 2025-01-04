package org.example.entities;

import org.example.builders.ModaliteEvaluationBuilder;
import org.example.builders.ModuleBuilder;
import org.example.enums.ModaliteEvaluationType;

public class ModaliteEvaluation {
    private Long id;
    private ElementDeModule elementDeModule;
    private ModaliteEvaluationType modaliteEvaluationType;
    private double coefficient;

    public ModaliteEvaluation() {
    }

    public ModaliteEvaluation(Long id, ElementDeModule elementDeModule, ModaliteEvaluationType modaliteEvaluationType, double coefficient) {
        this.id = id;
        this.elementDeModule = elementDeModule;
        this.modaliteEvaluationType = modaliteEvaluationType;
        this.coefficient = coefficient;
    }

 
    public Long getId() { 
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ElementDeModule getElementDeModule() {
        return elementDeModule;
    }

    public void setElementDeModule(ElementDeModule elementDeModule) {
        this.elementDeModule = elementDeModule;
    }

    public ModaliteEvaluationType getModaliteEvaluationType() {
        return modaliteEvaluationType;
    }

    public void setModaliteEvaluationType(ModaliteEvaluationType modaliteEvaluationType) {
        this.modaliteEvaluationType = modaliteEvaluationType;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public static ModaliteEvaluationBuilder builder() {
        return new ModaliteEvaluationBuilder();
    }
}
