package org.example.enums;

public enum ModaliteEvaluationType {
    CC("cc"),
    TP("tp"),
    PROJECT("project");

    private final String modaliteEvaluationType;

    ModaliteEvaluationType(String modaliteEvaluationType) {
        this.modaliteEvaluationType = modaliteEvaluationType;
    }

    public String getModaliteEvaluationType() {
        return modaliteEvaluationType;
    }
}
