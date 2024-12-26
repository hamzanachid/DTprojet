package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.UtilisateurDAO;
import org.example.entities.Utilisateur;
import org.example.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    private static UtilisateurDAOImpl instance;
    private final DatabaseConnection connectionManager;

    private UtilisateurDAOImpl(DatabaseConnection connectionManager) {
        this.connectionManager = connectionManager;
    }

    public static UtilisateurDAO getInstance(DatabaseConnection connectionManager) {
        if (instance == null) {
            instance = new UtilisateurDAOImpl(connectionManager);
        }
        return instance;
    }

    @Override
    public Utilisateur create(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (nom, prenom, login, motDePasse, role) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getLogin());
            stmt.setString(4, utilisateur.getMotDePasse());
            stmt.setString(5, utilisateur.getRole().name());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                utilisateur.setId(rs.getLong("id"));
                return utilisateur;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
        return null;
    }

    @Override
    public Optional<Utilisateur> findById(Long id) {
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToUtilisateur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Utilisateur> findByLogin(String login) {
        String sql = "SELECT * FROM utilisateurs WHERE login = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToUtilisateur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by login", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM utilisateurs";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                utilisateurs.add(mapResultSetToUtilisateur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all users", e);
        }
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> findByRole(Role role) {
        String sql = "SELECT * FROM utilisateurs WHERE role = ?";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                utilisateurs.add(mapResultSetToUtilisateur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding users by role", e);
        }
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> findByNom(String nom) {
        String sql = "SELECT * FROM utilisateurs WHERE nom = ?";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                utilisateurs.add(mapResultSetToUtilisateur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding users by nom", e);
        }
        return utilisateurs;
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET nom = ?, prenom = ?, login = ?, motDePasse= ?, role = ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getLogin());
            stmt.setString(4, utilisateur.getMotDePasse());
            stmt.setString(5, utilisateur.getRole().name());
            stmt.setLong(6, utilisateur.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return utilisateur;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
        return null;
    }

    @Override
    public boolean updateMotDePasse(Long id, String newMotDePasse) {
        String sql = "UPDATE utilisateurs SET motDePasse= ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newMotDePasse);
            stmt.setLong(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating password", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    @Override
    public Optional<Utilisateur> authenticate(String login, String motDePasse) {
        String sql = "SELECT * FROM utilisateurs WHERE login = ? AND motDePasse= ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToUtilisateur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error authenticating user", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByLogin(String login) {
        String sql = "SELECT COUNT(*) FROM utilisateurs WHERE login = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking login existence", e);
        }
        return false;
    }

    @Override
    public List<Utilisateur> saveAll(List<Utilisateur> utilisateurs) {
        List<Utilisateur> savedUtilisateurs = new ArrayList<>();
        for (Utilisateur utilisateur : utilisateurs) {
            Utilisateur saved = create(utilisateur);
            if (saved != null) {
                savedUtilisateurs.add(saved);
            }
        }
        return savedUtilisateurs;
    }

    private Utilisateur mapResultSetToUtilisateur(ResultSet rs) throws SQLException {
        return new Utilisateur(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("login"),
                rs.getString("motDePasse"),
                Role.valueOf(rs.getString("role"))
        );
    }
}