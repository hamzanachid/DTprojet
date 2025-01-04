package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.ElementDeModuleDAO;
import org.example.dao.ModuleDao;
import org.example.entities.ElementDeModule;
import org.example.entities.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElementDeModuleDaoImpl implements ElementDeModuleDAO {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();

    public static final ElementDeModuleDAO instance = new ElementDeModuleDaoImpl();

    private ElementDeModuleDaoImpl() {
    }
    @Override
    public ElementDeModule create(ElementDeModule elementDeModule) {
        String query = "INSERT INTO elementDeModule (nom, coefficient, module_id, filiere_id) VALUES (?, ?,?,?) RETURNING id;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, elementDeModule.getNom());
            statement.setDouble(2, elementDeModule.getCoefficient());
            statement.setLong(3, elementDeModule.getModule().getId());
            statement.setLong(4, elementDeModule.getFiliere().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                elementDeModule.setId(resultSet.getLong("id"));
            }
            return elementDeModule;
        } catch (SQLException e) {
            e.printStackTrace();
            return elementDeModule;
        }
    }

    @Override
    public ElementDeModule findByName(String name) {
        String query = "SELECT * FROM elementDeModule WHERE nom = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToElementDeModuleDao(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ElementDeModule findById(Long id) {
        String query = "SELECT * FROM elementDeModule WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToElementDeModuleDao(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(ElementDeModule elementDeModule) {
        String query = "UPDATE elementDeModule SET nom = ?, coefficient = ? WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, elementDeModule.getNom());
            statement.setDouble(2, elementDeModule.getCoefficient());
            statement.setLong(3, elementDeModule.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM elementDeModule WHERE id = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ElementDeModule> findAll() {
        String query = "SELECT * FROM elementDeModule;";
        List<ElementDeModule> elementDeModules = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                elementDeModules.add(mapToElementDeModuleDao(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementDeModules;
    }

    @Override
    public List<ElementDeModule> findByFiliereId(Long filiereId) {
        String query = "SELECT * FROM elementDeModule where filiere_id=?;";
        List<ElementDeModule> elementDeModules = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);


            ) {

            statement.setLong(1, filiereId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                elementDeModules.add(mapToElementDeModuleDao(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementDeModules;
    }

    @Override
    public List<ElementDeModule> findByModuleId(Long moduleId) {
        String query = "SELECT * FROM elementDeModule where module_id=?;";
        List<ElementDeModule> elementDeModules = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);


        ) {

            statement.setLong(1, moduleId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                elementDeModules.add(mapToElementDeModuleDao(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elementDeModules;
    }

    @Override
    public void updateModuleAverage(Long moduleId, double moduleAverage) {

    }

    private ElementDeModule mapToElementDeModuleDao(ResultSet resultSet) throws SQLException {
        ElementDeModule elementDeModule = new ElementDeModule();
        elementDeModule.setId(resultSet.getLong("id"));
        elementDeModule.setNom(resultSet.getString("nom"));
        elementDeModule.setCoefficient(resultSet.getFloat("coefficient"));

        ModuleDao moduleDao = ModuleDaoImpl.instance;
        Module module=moduleDao.findById(resultSet.getLong("module_id"));
        elementDeModule.setModule(module);

        return elementDeModule;
    }
}
