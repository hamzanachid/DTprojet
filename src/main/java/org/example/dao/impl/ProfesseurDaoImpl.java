package org.example.dao.impl;

import org.example.dao.config.DatabaseConnection;
import org.example.dao.ProfesseurDao;
import org.example.entities.Professeur;
import org.example.entities.Utilisateur;
import org.example.services.UtilisateurService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesseurDaoImpl implements ProfesseurDao {
    private static ProfesseurDao instance;
    private final DatabaseConnection databaseConnection;
    private final UtilisateurService utilisateurService;
    public static ProfesseurDao getInstance(DatabaseConnection databaseConnection, UtilisateurService utilisateurService) {
        if (instance == null)
            instance = new ProfesseurDaoImpl(databaseConnection, utilisateurService);
        return instance;
    }

    private ProfesseurDaoImpl(DatabaseConnection databaseConnection, UtilisateurService utilisateurService) {
        this.databaseConnection = databaseConnection;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public Professeur create(Professeur professeur) {

        String query = "INSERT INTO professeur (nom, prenom, specialite, code, utilisateur_id) VALUES(?, ?, ?, ?, ?) RETURNING id;";
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, professeur.getNom());
            statement.setString(2, professeur.getPrenom());
            statement.setString(3, professeur.getSpecialite());
            statement.setString(4, professeur.getCode());
            statement.setLong(5, professeur.getUtilisateur().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                professeur.setId(resultSet.getLong("id"));
            }
            return professeur;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Professeur> findById(Long id) {
        String query = "SELECT * FROM professeur WHERE id = ?;";
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToProfesseur(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Professeur> findByCode(String code) {
        String query = "SELECT * FROM professeur WHERE code LIKE ?;";
        try (
          Connection connection = databaseConnection.getConnection();
          PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToProfesseur(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Professeur> findByUserId(Long utilisateurId) {
        String query = "SELECT * FROM professeur WHERE utilisateur_id = ?;";
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, utilisateurId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToProfesseur(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Professeur> findAll() {
        String query = "SELECT * FROM professeur;";
        List<Professeur> professeurs = new ArrayList<>();
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                professeurs.add(mapToProfesseur(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professeurs;
    }

    @Override
    public void update(Professeur professeur) {
        String query = "UPDATE professeur SET nom = ?, prenom = ?, specialite = ?, code = ?;";
        try (
          Connection connection = databaseConnection.getConnection();
          PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, professeur.getNom());
            statement.setString(2, professeur.getPrenom());
            statement.setString(3, professeur.getSpecialite());
            statement.setString(4, professeur.getCode());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM professeur WHERE id = ?;";
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Professeur mapToProfesseur(ResultSet resultSet) throws SQLException {
        Professeur professeur = new Professeur();
        professeur.setId(resultSet.getLong("id"));
        professeur.setNom(resultSet.getString("nom"));
        professeur.setPrenom(resultSet.getString("prenom"));
        professeur.setSpecialite(resultSet.getString("specialite"));
        professeur.setCode(resultSet.getString("code"));
        professeur.setUtilisateur(extractUtilisateur((long) resultSet.getInt("utilisateur_id")));
        return professeur;
    }

    private Utilisateur extractUtilisateur(Long utilisateurId) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(utilisateurId);
        return utilisateur.orElse(null);
    }
}