package org.example.validation;

import org.example.entities.Etudiant;
import java.util.Arrays;
import java.util.List;

public class CompositeValidationStrategy implements ValidationStrategy {
    private final List<ValidationStrategy> strategies;

    public CompositeValidationStrategy(ValidationStrategy... strategies) {
        this.strategies = Arrays.asList(strategies);
    }

    @Override
    public void validate(Etudiant student) {
        for (ValidationStrategy strategy : strategies) {
            strategy.validate(student);
        }
    }
}