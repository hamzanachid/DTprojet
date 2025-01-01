package org.example.entities;

import java.util.List;

public class Etudiant {
    private Long id;
    private String firstName;
    private String lastName;
    private String matricule;

    // not in table etudiant
    private Filiere filiere;
    private List<Module> modules;
    private List<Note> notes;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Etudiant() {
    }

    public Etudiant(Long id, String firstName, String lastName, String matricule, Filiere filiere) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.matricule = matricule;
        this.filiere = filiere;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}