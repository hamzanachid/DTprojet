package org.example.entities;

import org.example.entities.builders.NoteBuilder;

public class Note {
    private int id;
    private Etudiant etudiant;
    private ModaliteEvaluation modaliteEvaluation;
    private double note;

    public Note() {
    }

    public Note(int id, Etudiant etudiant, ModaliteEvaluation modaliteEvaluation, double note, boolean absence, boolean validation) {
        this.id = id;
        this.etudiant = etudiant;
        this.modaliteEvaluation = modaliteEvaluation;
        this.note = note;
        this.absence = absence;
        this.validation = validation;
    }

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
    public static NoteBuilder builder(){
       return  new NoteBuilder();
    }
}
