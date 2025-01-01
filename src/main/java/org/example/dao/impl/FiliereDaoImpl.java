package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.FiliereDao;
import org.example.dao.UtilisateurDAO;
import org.example.entities.Filiere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FiliereDaoImpl implements FiliereDao {


    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();

    public final static FiliereDao instance = new FiliereDaoImpl();

    private FiliereDaoImpl() {
    }


    @Override
    public Filiere create(Filiere filiere) {
        return null;
    }

}
