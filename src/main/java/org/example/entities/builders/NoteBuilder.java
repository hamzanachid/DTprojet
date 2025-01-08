package org.example.entities.builders;

import org.example.entities.Etudiant;
import org.example.entities.ModaliteEvaluation;
import org.example.entities.Note;

public class NoteBuilder {
        private int id;
        private Etudiant etudiant;
        private ModaliteEvaluation modaliteEvaluation;
        private double note;
        private boolean absence;
        private boolean validation;


        public NoteBuilder setId(int id) {

            this.id = id;
            return this;
        }


        public NoteBuilder setEtudiant(Etudiant etudiant) {

            this.etudiant = etudiant;
            return this;
        }


        public NoteBuilder setModaliteEvaluation(ModaliteEvaluation modaliteEvaluation) {
            this.modaliteEvaluation = modaliteEvaluation;
            return this;
        }


        public NoteBuilder setNote(double note) {

            this.note = note;
            return this;
        }


        public NoteBuilder setAbsence(boolean absence) {
            this.absence = absence;
            return this;
        }


        public NoteBuilder setValidation(boolean validation) {

            this.validation = validation;
            return this;
        }

        public Note build(){
            return new Note(id,etudiant,modaliteEvaluation,note,absence,validation);
        }


}
