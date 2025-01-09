package entities;

import org.example.entities.Professeur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfesseurTest {
  @Test
  public void TestNoArgsConstructor(){
    Professeur professeur = new Professeur();
    assertNotNull(professeur);
  }

  @Test
  public void TestBuilder(){
    Professeur professeur = Professeur.builder()
      .setNom("Blidi")
      .setPrenom("Ilyas")
      .setSpecialite("Developpement")
      .setCode("PROF123")
      .build();

    assertEquals("Blidi", professeur.getNom());
    assertEquals("Ilyas", professeur.getPrenom());
    assertEquals("Developpement", professeur.getSpecialite());
    assertEquals("PROF123", professeur.getCode());
  }
}
