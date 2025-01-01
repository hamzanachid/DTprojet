package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.ProfesseurDao;
import org.example.entities.Professeur;
import org.example.entities.Utilisateur;
import org.example.enums.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProfesseurDaoImpl implements ProfesseurDao {
  public final static ProfesseurDao instance = new ProfesseurDaoImpl();

  private ProfesseurDaoImpl() {}

  private Connection getConnection() throws SQLException {
    return DatabaseConnection.getInstance().getConnection();
  }

  @Override
  public Professeur create(Professeur professeur) {
    // SQL queries for inserting data into the tables
    String utilisateurSql = "INSERT INTO utilisateur (login, mot_de_passe, role) VALUES (?, ?, ?) RETURNING id";
    String professeurSql = "INSERT INTO professeur (id, nom, prenom, specialite, code) VALUES (?, ?, ?, ?, ?)";

    Connection connection = null;

    try {
      connection = getConnection();
      connection.setAutoCommit(false); // Start transaction

      // Save Utilisateur first
      try (PreparedStatement utilisateurStmt = connection.prepareStatement(utilisateurSql)) {
        utilisateurStmt.setString(1, professeur.getLogin());
        utilisateurStmt.setString(2, professeur.getMotDePasse());
        utilisateurStmt.setString(3, professeur.getRole().name());

        ResultSet utilisateurResultSet = utilisateurStmt.executeQuery();

        if (utilisateurResultSet.next()) {
          long utilisateurId = utilisateurResultSet.getLong("id");
          professeur.setId(utilisateurId); // Set the ID in Professeur

          // Save Professeur
          try (PreparedStatement professeurStmt = connection.prepareStatement(professeurSql)) {
            professeurStmt.setLong(1, utilisateurId); // Link Professeur to Utilisateur
            professeurStmt.setString(2, professeur.getNom());
            professeurStmt.setString(3, professeur.getPrenom());
            professeurStmt.setString(4, professeur.getSpecialite());
            professeurStmt.setString(5, professeur.getCode());
            professeurStmt.executeUpdate();

            connection.commit(); // Commit the transaction
          }
        } else {
          throw new SQLException("Failed to create Utilisateur record");
        }

      } catch (SQLException e) {
        // Rollback transaction in case of an error
        if (connection != null) {
          connection.rollback();
        }
        e.printStackTrace();
        return null;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    } finally {
      // Ensure the connection is closed
      try {
        if (connection != null && !connection.isClosed()) {
          connection.setAutoCommit(true); // Restore default auto-commit
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }

    return professeur;
  }

  @Override
  public Optional<Professeur> findById(Long id) {
    String utilisateurSql = "SELECT * FROM utilisateur WHERE id = ?";
    String professeurSql = "SELECT * FROM professeur WHERE id = ?";

    Connection connection = null;

    try {
      connection = getConnection();

      // Fetch Utilisateur data
      Utilisateur utilisateur = null;
      try (PreparedStatement stmt = connection.prepareStatement(utilisateurSql)) {
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
          utilisateur = new Utilisateur(
            rs.getLong("id"),
            rs.getString("login"),
            rs.getString("mot_de_passe"),
            Role.valueOf(rs.getString("role"))
          );
        } else {
          return Optional.empty(); // No user found
        }
      }

      // Fetch Professeur data
      Professeur professeur = null;
      try (PreparedStatement stmt = connection.prepareStatement(professeurSql)) {
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
          professeur = new Professeur(
            utilisateur.getId(),
            utilisateur.getLogin(),
            utilisateur.getMotDePasse(),
            utilisateur.getRole(),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("specialite"),
            rs.getString("code"),
            null // elementsDeModule is not fetched in this example
          );
        }
      }

      return Optional.ofNullable(professeur);
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public Optional<Professeur> findByLogin(String login) {
    String utilisateurSql = "SELECT * FROM utilisateur WHERE login = ?";
    String professeurSql = "SELECT * FROM professeur WHERE id = ?";

    Connection connection = null;

    try {
      connection = getConnection();

      // Fetch Utilisateur data by login
      Utilisateur utilisateur = null;
      try (PreparedStatement stmt = connection.prepareStatement(utilisateurSql)) {
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
          utilisateur = new Utilisateur(
            rs.getLong("id"),
            rs.getString("login"),
            rs.getString("mot_de_passe"),
            Role.valueOf(rs.getString("role"))
          );
        } else {
          return Optional.empty(); // No user found with the given login
        }
      }

      // Fetch Professeur data using the Utilisateur ID
      Professeur professeur = null;
      try (PreparedStatement stmt = connection.prepareStatement(professeurSql)) {
        stmt.setLong(1, utilisateur.getId());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
          professeur = new Professeur(
            utilisateur.getId(),
            utilisateur.getLogin(),
            utilisateur.getMotDePasse(),
            utilisateur.getRole(),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("specialite"),
            rs.getString("code"),
            null // elementsDeModule is not fetched in this example
          );
        }
      }

      return Optional.ofNullable(professeur);
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public List<Professeur> findAll() {
    String utilisateurSql = "SELECT * FROM utilisateur";
    String professeurSql = "SELECT * FROM professeur WHERE id = ?";

    Connection connection = null;
    List<Professeur> professors = new ArrayList<>();

    try {
      connection = getConnection();

      // Fetch all Utilisateur data
      try (PreparedStatement utilisateurStmt = connection.prepareStatement(utilisateurSql);
           ResultSet utilisateurRs = utilisateurStmt.executeQuery()) {

        while (utilisateurRs.next()) {
          Long utilisateurId = utilisateurRs.getLong("id");
          String login = utilisateurRs.getString("login");
          String motDePasse = utilisateurRs.getString("mot_de_passe");
          Role role = Role.valueOf(utilisateurRs.getString("role"));

          // Fetch the corresponding Professeur data
          Professeur professeur = null;
          try (PreparedStatement professeurStmt = connection.prepareStatement(professeurSql)) {
            professeurStmt.setLong(1, utilisateurId);
            ResultSet professeurRs = professeurStmt.executeQuery();

            if (professeurRs.next()) {
              professeur = new Professeur(
                utilisateurId,
                login,
                motDePasse,
                role,
                professeurRs.getString("nom"),
                professeurRs.getString("prenom"),
                professeurRs.getString("specialite"),
                professeurRs.getString("code"),
                null // elementsDeModule is not fetched in this example
              );
            }
          }

          if (professeur != null) {
            professors.add(professeur);
          }
        }
      }

      return professors;
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public List<Professeur> findByRole(Role role) {
    String utilisateurSql = "SELECT * FROM utilisateur WHERE role = ?";
    String professeurSql = "SELECT * FROM professeur WHERE id = ?";

    Connection connection = null;
    List<Professeur> professors = new ArrayList<>();

    try {
      connection = getConnection();

      // Fetch Utilisateur data by role
      try (PreparedStatement utilisateurStmt = connection.prepareStatement(utilisateurSql)) {
        utilisateurStmt.setString(1, role.name()); // Filter by role
        ResultSet utilisateurRs = utilisateurStmt.executeQuery();

        while (utilisateurRs.next()) {
          Long utilisateurId = utilisateurRs.getLong("id");
          String login = utilisateurRs.getString("login");
          String motDePasse = utilisateurRs.getString("mot_de_passe");

          // Fetch the corresponding Professeur data
          Professeur professeur = null;
          try (PreparedStatement professeurStmt = connection.prepareStatement(professeurSql)) {
            professeurStmt.setLong(1, utilisateurId);
            ResultSet professeurRs = professeurStmt.executeQuery();

            if (professeurRs.next()) {
              professeur = new Professeur(
                utilisateurId,
                login,
                motDePasse,
                role,
                professeurRs.getString("nom"),
                professeurRs.getString("prenom"),
                professeurRs.getString("specialite"),
                professeurRs.getString("code"),
                null // elementsDeModule is not fetched in this example
              );
            }
          }

          if (professeur != null) {
            professors.add(professeur);
          }
        }
      }

      return professors;
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public List<Professeur> findByNom(String nom) {
    String utilisateurSql = "SELECT * FROM utilisateur WHERE id IN (SELECT id FROM professeur WHERE nom = ?)";
    String professeurSql = "SELECT * FROM professeur WHERE id = ?";

    Connection connection = null;
    List<Professeur> professors = new ArrayList<>();

    try {
      connection = getConnection();

      // Fetch Utilisateur data by matching 'nom'
      try (PreparedStatement utilisateurStmt = connection.prepareStatement(utilisateurSql)) {
        utilisateurStmt.setString(1, nom); // Match by 'nom'
        ResultSet utilisateurRs = utilisateurStmt.executeQuery();

        while (utilisateurRs.next()) {
          Long utilisateurId = utilisateurRs.getLong("id");
          String login = utilisateurRs.getString("login");
          String motDePasse = utilisateurRs.getString("mot_de_passe");
          Role role = Role.valueOf(utilisateurRs.getString("role"));

          // Fetch the corresponding Professeur data
          Professeur professeur = null;
          try (PreparedStatement professeurStmt = connection.prepareStatement(professeurSql)) {
            professeurStmt.setLong(1, utilisateurId);
            ResultSet professeurRs = professeurStmt.executeQuery();

            if (professeurRs.next()) {
              professeur = new Professeur(
                utilisateurId,
                login,
                motDePasse,
                role,
                professeurRs.getString("nom"),
                professeurRs.getString("prenom"),
                professeurRs.getString("specialite"),
                professeurRs.getString("code"),
                null // elementsDeModule is not fetched in this example
              );
            }
          }

          if (professeur != null) {
            professors.add(professeur);
          }
        }
      }

      return professors;
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public Professeur update(Professeur professeur) {
    String utilisateurSql = "UPDATE utilisateur SET login = ?, mot_de_passe = ?, role = ? WHERE id = ?";
    String professeurSql = "UPDATE professeur SET nom = ?, prenom = ?, specialite = ?, code = ? WHERE id = ?";

    Connection connection = null;

    try {
      connection = getConnection();

      // Start a transaction
      connection.setAutoCommit(false);

      // Update Utilisateur
      try (PreparedStatement utilisateurStmt = connection.prepareStatement(utilisateurSql)) {
        utilisateurStmt.setString(1, professeur.getLogin());
        utilisateurStmt.setString(2, professeur.getMotDePasse());
        utilisateurStmt.setString(3, professeur.getRole().name());
        utilisateurStmt.setLong(4, professeur.getId());

        int utilisateurRowsAffected = utilisateurStmt.executeUpdate();
        if (utilisateurRowsAffected == 0) {
          connection.rollback(); // Rollback if no utilisateur was updated
          return null;
        }

        // Update Professeur
        try (PreparedStatement professeurStmt = connection.prepareStatement(professeurSql)) {
          professeurStmt.setString(1, professeur.getNom());
          professeurStmt.setString(2, professeur.getPrenom());
          professeurStmt.setString(3, professeur.getSpecialite());
          professeurStmt.setString(4, professeur.getCode());
          professeurStmt.setLong(5, professeur.getId());

          int professeurRowsAffected = professeurStmt.executeUpdate();
          if (professeurRowsAffected == 0) {
            connection.rollback(); // Rollback if no professeur was updated
            return null;
          }

          connection.commit(); // Commit the transaction
        }
      }

      return professeur; // Return the updated professeur

    } catch (SQLException e) {
      e.printStackTrace();
      try {
        if (connection != null) {
          connection.rollback(); // Rollback in case of error
        }
      } catch (SQLException rollbackEx) {
        rollbackEx.printStackTrace();
      }
      return null;
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.setAutoCommit(true); // Restore default auto-commit
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public boolean updateMotDePasse(Long id, String newMotDePasse) {
    String sql = "UPDATE utilisateur SET mot_de_passe = ? WHERE id = ?";

    Connection connection = null;

    try {
      connection = getConnection();

      // Prepare the update statement
      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, newMotDePasse);
        stmt.setLong(2, id);

        int rowsAffected = stmt.executeUpdate();

        // If no rows were affected, the update failed
        return rowsAffected > 0;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public boolean delete(Long id) {
    String deleteProfesseurSql = "DELETE FROM professeur WHERE id = ?";
    String deleteUtilisateurSql = "DELETE FROM utilisateur WHERE id = ?";

    Connection connection = null;

    try {
      connection = getConnection();
      connection.setAutoCommit(false); // Start transaction

      // Delete Professeur record
      try (PreparedStatement professeurStmt = connection.prepareStatement(deleteProfesseurSql)) {
        professeurStmt.setLong(1, id);
        int professeurRowsAffected = professeurStmt.executeUpdate();

        if (professeurRowsAffected > 0) {
          // If Professeur record was deleted, delete the corresponding Utilisateur
          try (PreparedStatement utilisateurStmt = connection.prepareStatement(deleteUtilisateurSql)) {
            utilisateurStmt.setLong(1, id);
            int utilisateurRowsAffected = utilisateurStmt.executeUpdate();

            if (utilisateurRowsAffected > 0) {
              connection.commit(); // Commit the transaction if both deletions were successful
              return true;
            } else {
              connection.rollback(); // Rollback if deletion from 'utilisateur' failed
              return false;
            }
          }
        } else {
          connection.rollback(); // Rollback if deletion from 'professeur' failed
          return false;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        if (connection != null) {
          connection.rollback(); // Rollback in case of an error
        }
      } catch (SQLException rollbackEx) {
        rollbackEx.printStackTrace();
      }
      return false;
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.setAutoCommit(true); // Restore default auto-commit
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public Optional<Professeur> authenticate(String login, String motDePasse) {
    String utilisateurSql = "SELECT * FROM utilisateur WHERE login = ? AND mot_de_passe = ?";
    String professeurSql = "SELECT * FROM professeur WHERE id = ?";

    Connection connection = null;

    try {
      connection = getConnection();

      // Fetch Utilisateur data
      Utilisateur utilisateur = null;
      try (PreparedStatement stmt = connection.prepareStatement(utilisateurSql)) {
        stmt.setString(1, login);
        stmt.setString(2, motDePasse);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
          utilisateur = new Utilisateur(
            rs.getLong("id"),
            rs.getString("login"),
            rs.getString("mot_de_passe"),
            Role.valueOf(rs.getString("role"))
          );
        } else {
          return Optional.empty(); // No user found with provided login and password
        }
      }

      // Fetch Professeur data using the utilisateur ID
      Professeur professeur = null;
      try (PreparedStatement stmt = connection.prepareStatement(professeurSql)) {
        stmt.setLong(1, utilisateur.getId());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
          professeur = new Professeur(
            utilisateur.getId(),
            utilisateur.getLogin(),
            utilisateur.getMotDePasse(),
            utilisateur.getRole(),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("specialite"),
            rs.getString("code"),
            null // elementsDeModule is not fetched in this example
          );
        }
      }

      return Optional.ofNullable(professeur);
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    } finally {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }
  }

  @Override
  public boolean existsByLogin(String login) {
    String sql = "SELECT 1 FROM utilisateur WHERE login = ? LIMIT 1"; // Simple check to see if login exists

    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {

      stmt.setString(1, login);
      ResultSet rs = stmt.executeQuery();

      // If a row is returned, the login exists
      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public List<Professeur> saveAll(List<Professeur> professeurs) {
    String utilisateurSql = "INSERT INTO utilisateur (login, mot_de_passe, role) VALUES (?, ?, ?) RETURNING id";
    String professeurSql = "INSERT INTO professeur (id, nom, prenom, specialite, code) VALUES (?, ?, ?, ?, ?)";

    Connection connection = null;
    List<Professeur> savedProfesseurs = new ArrayList<>();

    try {
      connection = getConnection();
      connection.setAutoCommit(false); // Start transaction

      try (PreparedStatement utilisateurStmt = connection.prepareStatement(utilisateurSql);
           PreparedStatement professeurStmt = connection.prepareStatement(professeurSql)) {

        for (Professeur professeur : professeurs) {
          // Save Utilisateur first
          utilisateurStmt.setString(1, professeur.getLogin());
          utilisateurStmt.setString(2, professeur.getMotDePasse());
          utilisateurStmt.setString(3, professeur.getRole().name());

          ResultSet utilisateurResultSet = utilisateurStmt.executeQuery();

          if (utilisateurResultSet.next()) {
            long utilisateurId = utilisateurResultSet.getLong("id");
            professeur.setId(utilisateurId); // Set the ID in Professeur

            // Save Professeur
            professeurStmt.setLong(1, utilisateurId); // Link Professeur to Utilisateur
            professeurStmt.setString(2, professeur.getNom());
            professeurStmt.setString(3, professeur.getPrenom());
            professeurStmt.setString(4, professeur.getSpecialite());
            professeurStmt.setString(5, professeur.getCode());
            professeurStmt.executeUpdate();

            savedProfesseurs.add(professeur); // Add to saved list
          } else {
            throw new SQLException("Failed to create Utilisateur record for " + professeur.getLogin());
          }
        }

        connection.commit(); // Commit the transaction

      } catch (SQLException e) {
        // Rollback transaction in case of an error
        if (connection != null) {
          connection.rollback();
        }
        e.printStackTrace();
        return List.of(); // Return an empty list if something went wrong
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return List.of(); // Return an empty list in case of an error
    } finally {
      // Ensure the connection is closed
      try {
        if (connection != null && !connection.isClosed()) {
          connection.setAutoCommit(true); // Restore default auto-commit
          connection.close();
        }
      } catch (SQLException closeEx) {
        closeEx.printStackTrace();
      }
    }

    return savedProfesseurs; // Return the list of successfully saved Professeurs
  }
}
