package org.example.entities;

import org.example.enums.ModaliteEvaluationType;

public class ModaliteEvaluation {
    private int id;
    private ElementDeModule elementDeModule;
    private ModaliteEvaluationType modaliteEvaluationType;
    private double coefficient;

    public ModaliteEvaluation() {
    }

    public ModaliteEvaluation(int id, ElementDeModule elementDeModule, ModaliteEvaluationType modaliteEvaluationType, double coefficient) {
        this.id = id;
        this.elementDeModule = elementDeModule;
        this.modaliteEvaluationType = modaliteEvaluationType;
        this.coefficient = coefficient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
