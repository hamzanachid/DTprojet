package org.example.validation;

import org.example.entities.Etudiant;

public class MatriculeValidationStrategy implements ValidationStrategy {
    @Override
    public void validate(Etudiant student) {
        String matricule = student.getMatricule();
        if (!matricule.matches("^[0-9]{3}$")) {
            throw new IllegalArgumentException("matricule must be 3 digits");
        }
    }
}