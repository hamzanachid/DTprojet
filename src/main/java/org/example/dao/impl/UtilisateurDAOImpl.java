package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.UtilisateurDAO;
import org.example.entities.Utilisateur;

import java.sql.*;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    private static UtilisateurDAOImpl instance;
    private DatabaseConnection connectionManager;

    private UtilisateurDAOImpl(DatabaseConnection connectionManager) {
        this.connectionManager = connectionManager;
    }

    public static UtilisateurDAOImpl getInstance(DatabaseConnection connectionManager) {
        if (instance == null) {
            instance = new UtilisateurDAOImpl(connectionManager);
        }
        return instance;
    }

    @Override
    public void save(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (id, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, utilisateur.getId());
            stmt.setString(2, utilisateur.getUsername());
            stmt.setString(3, utilisateur.getPassword());
            stmt.setString(4, utilisateur.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur findById(Long id) {
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilisateur findByUsername(String username) {
        String sql = "SELECT * FROM utilisateurs WHERE username = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getUsername());
            stmt.setString(2, utilisateur.getPassword());
            stmt.setString(3, utilisateur.getRole());
            stmt.setLong(4, utilisateur.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur login(String username, String password) {
        String sql = "SELECT * FROM utilisateurs WHERE username = ? AND password = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void signUp(Utilisateur utilisateur) {
        if (findByUsername(utilisateur.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        save(utilisateur);
    }

    // Helper method to map ResultSet to Utilisateur entity
    private Utilisateur mapResultSetToUtilisateur(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");

        return new Utilisateur(id, username, password, role);
    }
}
