package be.odisee.citymesh;

import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.service.MeldingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MeldingTest {
    @Autowired
    private MeldingService meldingService;

    @Test
    public void testGenerateOvercrowdingReports() {
        // Simuleer een matrix met te hoge waarden
        int[][] matrix = {
                {10, 20, 30},
                {40, 100, 50}, // Cel (1,1) is te druk (100)
                {20, 10, 5}
        };
        int limit = 50; // Alles boven 50 moet een melding genereren

        // Roep de functie aan
        List<Melding> meldingen = meldingService.generateOvercrowdingReports(matrix, limit);

        // Controleer dat er precies 1 meldingen zijn (cel (1,1))
        assertEquals(1, meldingen.size());

        // Controleer de melding
        Melding eersteMelding = meldingen.get(0);
        assertEquals("Te veel mensen op locatie (1, 1): 100 personen.", eersteMelding.getBeschrijving());
        assertEquals(Melding.MeldingStatus.OVERCROWDING, eersteMelding.getStatus());
        assertNotNull(eersteMelding.getTijdstip());
    }
}
