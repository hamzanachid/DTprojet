package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.ElementDeModuleDAO;
import org.example.dao.FiliereDao;
import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FiliereDaoImpl implements FiliereDao {

    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();

    public static final FiliereDao instance = new FiliereDaoImpl();

    private FiliereDaoImpl() {
    }

    @Override
    public Filiere create(Filiere filiere) {
        String query = "INSERT INTO filiere (nom, code) VALUES (?, ?) RETURNING id;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, filiere.getNom());
            statement.setString(2, filiere.getCode());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                filiere.setId(resultSet.getLong("id"));
            }
            return filiere;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Filiere findById(Long id) {
        String query = "SELECT * FROM filiere WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToFiliere(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Filiere filiere ) {
        String query = "UPDATE filiere SET nom = ?, code = ? WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, filiere.getNom());
            statement.setString(2, filiere.getCode());
            statement.setLong(3, filiere.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM filiere WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Filiere> findAll() {
        String query = "SELECT * FROM filiere;";
        List<Filiere> filieres = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                filieres.add(mapToFiliere(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filieres;
    }

    private Filiere mapToFiliere(ResultSet resultSet) throws SQLException {
        Filiere filiere = new Filiere();
        filiere.setId(resultSet.getLong("id"));
        filiere.setNom(resultSet.getString("nom"));
        filiere.setCode(resultSet.getString("code"));
        return filiere;
    }
}
