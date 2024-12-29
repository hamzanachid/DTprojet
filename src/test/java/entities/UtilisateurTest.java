package entities;

import org.example.config.DatabaseConnection;
import org.example.entities.Utilisateur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UtilisateurTest {
    @Test
    public void TestConstructor(){
        Utilisateur utilisateur = new Utilisateur();
        assertNull(utilisateur.getNom());
    }
}
