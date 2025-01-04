package org.example.builders;

import org.example.entities.*;
import org.example.entities.Module;
import java.util.List;

public  class EtudiantBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private String matricule;
    // not in table etudiant
    private Filiere filiere;
    private List<Module> modules;
    private List<Note> notes;

    public EtudiantBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public EtudiantBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EtudiantBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EtudiantBuilder setMatricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public EtudiantBuilder setFiliere(Filiere filiere) {
        this.filiere = filiere;
        return this;
    }

    public EtudiantBuilder setModules(List<Module> modules) {
        this.modules = modules;
        return this;
    }

    public EtudiantBuilder setNotes(List<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Etudiant build() {
        return new Etudiant(id, firstName, lastName, matricule, filiere);
    }
}
