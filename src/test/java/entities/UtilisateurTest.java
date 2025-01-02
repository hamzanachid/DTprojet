package entities;

import org.example.entities.Utilisateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UtilisateurTest {
    @Test
    public void TestConstructor(){
        Utilisateur utilisateur = new Utilisateur();
        assertNotNull(utilisateur);
    }
}
