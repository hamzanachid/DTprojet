package org.example.entities;

import org.example.enums.Role;

import java.util.List;

public class Professeur extends Utilisateur {
    private Long id;
    private String specialite;
    private String code;
    private Utilisateur compteUtilisateur;
    private List<Module> modules;
    private List<ElementDeModule> elementsDeModule;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Utilisateur getCompteUtilisateur() {
        return compteUtilisateur;
    }

    public void setCompteUtilisateur(Utilisateur compteUtilisateur) {
        this.compteUtilisateur = compteUtilisateur;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<ElementDeModule> getElementsDeModule() {
        return elementsDeModule;
    }

    public void setElementsDeModule(List<ElementDeModule> elementsDeModule) {
        this.elementsDeModule = elementsDeModule;
    }

    public Professeur() {

    }

    public Professeur(Long id, String nom, String prenom, String login, String motDePasse, Role role, Long id1, String specialite, String code, Utilisateur compteUtilisateur, List<Module> modules, List<ElementDeModule> elementsDeModule) {
        super(id, nom, prenom, login, motDePasse, role);
        this.id = id1;
        this.specialite = specialite;
        this.code = code;
        this.compteUtilisateur = compteUtilisateur;
        this.modules = modules;
        this.elementsDeModule = elementsDeModule;
    }

    @Override
    public String toString() {

        return "Professeur{" +
                "id=" + id +
                ", specialite='" + specialite + '\'' +
                ", code='" + code + '\'' +
                ", compteUtilisateur=" + compteUtilisateur +
                ", modules=" + modules +
                ", elementsDeModule=" + elementsDeModule +
                '}';
    }
}