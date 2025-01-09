package entities;

import org.example.entities.Utilisateur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UtilisateurTest {
    @Test
    public void TestNoArgsConstructor(){
        Utilisateur utilisateur = new Utilisateur();
        assertNotNull(utilisateur);
    }
    @Test
    public void TestArgsConstructor(){
        Utilisateur utilisateur = new Utilisateur();
        assertNotNull(utilisateur);
    }
}
