package org.example;


import org.example.dao.ElementDeModuleDAO;
import org.example.dao.impl.ElementDeModuleDaoImpl;
import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.enums.Semestre;
import org.example.services.ElementDeModuleService;
import org.example.services.FiliereService;
import org.example.services.ModuleService;
import org.example.services.impl.ElementsDeModuleServiceImpl;
import org.example.services.impl.FiliereServiceImpl;
import org.example.services.impl.ModuleServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Filiere filiere=Filiere.builder().setCode("C").setNom("nom").build();
        FiliereService filiereService= FiliereServiceImpl.instance;
        Filiere filiere1=filiereService.create(filiere);
        Module module=Module
                .builder()
                .setCode("C")
                .setNom("Se")
                .setSemestre(Semestre.S1)
                .setFiliere(filiere1)
                .build();
        ModuleService moduleService= ModuleServiceImpl.instance;
        Module module1=moduleService.create(module);
        ElementDeModule elementDeModule=ElementDeModule
                .builder()
                .setModule(module1)
                .setFiliere(filiere1)
                .setNom("Nom")
                .setCoefficient(82.8)
                .build();
        ElementDeModuleService elementDeModuleService=ElementsDeModuleServiceImpl.instance;
        ElementDeModule e=elementDeModuleService.create(elementDeModule);
        System.out.println(e);
    }
}
