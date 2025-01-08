package org.example.validation;

import org.example.entities.Etudiant;

public interface ValidationStrategy {
    void validate(Etudiant student) throws IllegalArgumentException;
}