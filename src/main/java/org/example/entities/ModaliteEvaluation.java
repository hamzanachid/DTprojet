package org.example.entities;

import org.example.enums.ModaliteEvaluationType;

public class ModaliteEvaluation {
    private int id;
    private ElementDeModule elementDeModule;
    private ModaliteEvaluationType modaliteEvaluationType; // e.g., "CC", "TP", "Projet", etc.
    private double coefficient;
 
}
