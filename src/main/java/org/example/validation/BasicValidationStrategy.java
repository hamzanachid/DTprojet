package org.example.validation;

import org.example.entities.Etudiant;

public class BasicValidationStrategy implements ValidationStrategy{
    @Override
    public void validate(Etudiant student) {
        if (student == null) {
            throw new IllegalArgumentException("student is required");
        }
        if (isEmpty(student.getFirstName())) {
            throw new IllegalArgumentException("first name is required");
        }
        if (isEmpty(student.getLastName())) {
            throw new IllegalArgumentException("last name is required");
        }
        if (isEmpty(student.getMatricule())) {
            throw new IllegalArgumentException("matricule is required");
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
