package be.odisee.citymesh.service;

import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.domain.Verslag;

import java.util.List;

/**
 * Interface voor het verwerken van meldingen binnen het dronesysteem van Citymesh.
 * <p>
 * De meldingen worden gegenereerd op basis van situaties zoals overbevolking, lage batterij,
 * of onderhoudsbehoeften. Deze service biedt ook ondersteuning voor het samenstellen van rapporten.
 */
public interface MeldingService {

    /**
     * Genereert een lijst van meldingen op basis van een simulatiematrix en een drempelwaarde
     * voor overbevolking. Deze methode is hier als `static` gedefinieerd, wat niet gebruikelijk is in interfaces;
     * in de praktijk zou deze beter in een implementatieklasse staan.
     *
     * @param matrix de simulatiematrix waarin het aantal personen per cel staat
     * @param limit  de drempelwaarde waarboven een overbevolkingsmelding gegenereerd moet worden
     * @return een lijst met gegenereerde {@link Melding} objecten
     */
    List<Melding> generateOvercrowdingReports(int[][] matrix, int limit);
	
	//    Verslag verwerkMeldingen(List<Melding> meldingen);

}
