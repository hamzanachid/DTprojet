package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.ElementDeModuleDAO;
import org.example.dao.FiliereDao;
import org.example.dao.ModuleDao;
import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.enums.Semestre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDaoImpl implements ModuleDao {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();

    public static final ModuleDao instance = new ModuleDaoImpl();

    private ModuleDaoImpl() {
    }

    @Override
    public Module findByName(String name) {
        String sql = "SELECT * FROM modules WHERE nom = ?";
        Module module = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                module = mapResultSetToModule(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public Module create(Module module) {
        String sql = "INSERT INTO modules (code, nom, semester,filiere_id) VALUES (?, ? , ?,?) RETURNING id;";
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, module.getCode());
            statement.setString(2, module.getNom());
            statement.setString(3, module.getSemestre().name());
            statement.setLong(4, module.getFiliere().getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    module.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public void update(Module module) {
        String sql = "UPDATE modules SET code = ?, nom = ? , semester = ?,filiere_id=? WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, module.getCode());
            statement.setString(2, module.getNom());
            statement.setString(3, module.getSemestre().name());
            statement.setLong(4, module.getFiliere().getId());
            statement.setLong(5, module.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM modules WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Module findById(Long id) {
        String sql = "SELECT * FROM modules WHERE id = ?";
        Module module = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                module = mapResultSetToModule(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public List<Module> findAll() {
        String sql = "SELECT * FROM modules";
        List<Module> modules = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                modules.add(mapResultSetToModule(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    private Module mapResultSetToModule(ResultSet resultSet) throws SQLException {
        Module module = new Module();
        System.out.println(resultSet.getLong("filiere_id"));
        module.setId(resultSet.getLong("id"));
        module.setCode(resultSet.getString("code"));
        module.setNom(resultSet.getString("nom"));

        module.setSemestre(Semestre.valueOf(resultSet.getString("semester")));

        FiliereDao filiereDao=FiliereDaoImpl.instance;
        Filiere filiere=filiereDao.findById(resultSet.getLong("filiere_id"));
        module.setFiliere(filiere);


        return module;
    }
}
