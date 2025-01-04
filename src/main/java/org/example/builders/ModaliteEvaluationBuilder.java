package org.example.builders;

import org.example.entities.ElementDeModule;
import org.example.entities.ModaliteEvaluation;
import org.example.enums.ModaliteEvaluationType;

public class ModaliteEvaluationBuilder {
    private Long id;
    private ElementDeModule elementDeModule;
    private ModaliteEvaluationType modaliteEvaluationType;
    private double coefficient;

    public ModaliteEvaluationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ModaliteEvaluationBuilder setElementDeModule(ElementDeModule elementDeModule) {
        this.elementDeModule = elementDeModule;
        return this;
    }

    public ModaliteEvaluationBuilder setModaliteEvaluationType(ModaliteEvaluationType modaliteEvaluationType) {
        this.modaliteEvaluationType = modaliteEvaluationType;
        return this;
    }

    public ModaliteEvaluationBuilder setCoefficient(double coefficient) {
        this.coefficient = coefficient;
        return this;
    }

    public ModaliteEvaluation build() {
        return new ModaliteEvaluation(id, elementDeModule, modaliteEvaluationType, coefficient);
    }
}
