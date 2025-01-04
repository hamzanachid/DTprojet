package org.example.entities;

public class Note {
    private int id;
    private Etudiant etudiant;
    private ModaliteEvaluation modaliteEvaluation;
    private double note;
    private boolean absence;
    private boolean validation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public ModaliteEvaluation getModaliteEvaluation() {
        return modaliteEvaluation;
    }

    public void setModaliteEvaluation(ModaliteEvaluation modaliteEvaluation) {
        this.modaliteEvaluation = modaliteEvaluation;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public boolean isAbsence() {
        return absence;
    }

    public void setAbsence(boolean absence) {
        this.absence = absence;
    }

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }
}
