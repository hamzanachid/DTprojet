package org.example.services.impl;

import org.example.dao.FiliereDao;
import org.example.dao.impl.FiliereDaoImpl;
import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.services.FiliereService;

import java.util.List;

public class FiliereServiceImpl implements FiliereService {
    private static FiliereDao filiereDao = FiliereDaoImpl.instance;
    final static FiliereService instance = new FiliereServiceImpl();

    private FiliereServiceImpl(){
    }

    @Override
    public Filiere create(Filiere filiere) {
        return null;
    }

}
