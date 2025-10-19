package be.odisee.citymeshclient;

import be.odisee.citymeshclient.domain.Drone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CityMeshClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityMeshClientApplication.class, args);

        String baseUrl = "http://localhost:8080/api/drone";

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        System.out.println("________________________");

        // ---- GET /api/drone → alle drones ----
        List<Drone> drones = webClient
                .get()
                .uri("")
                .retrieve()
                .bodyToFlux(Drone.class)
                .collectList()
                .block();

        System.out.println("\nVia REST haalden we volgende drones op: ");
        for (Drone d : drones) {
            System.out.println(d);
        }

        // ---- GET /api/drone/{id} → één specifieke drone ----

        Drone drone = webClient
                .get()
                .uri("/1")
                .retrieve()
                .bodyToMono(Drone.class)
                .block();

        System.out.println("Via REST haalden we volgende drone op: " + drone);

        // ---- POST /api/drone → nieuwe drone toevoegen ----

        Drone nieuweDrone = new Drone(
                Drone.DroneStatus.INACTIVE,
                "Park Zuid",
                Drone.DroneType.QUADCOPTER,   // Of eender welk type
                100
        );

        Drone aangemaakteDrone = webClient
                .post()
                .uri("")
                .body(Mono.just(nieuweDrone), Drone.class)
                .retrieve()
                .bodyToMono(Drone.class)
                .block();

        System.out.println("\nNieuwe drone toegevoegd: " + aangemaakteDrone);

        // ---- PUT /api/drone/{id} → bestaande drone bijwerken ----

        aangemaakteDrone.setType(Drone.DroneType.RACING_DRONE); // of ander type
        Drone aangepasteDrone = webClient
                .put()
                .uri("/" + aangemaakteDrone.getDroneID())
                .body(Mono.just(aangemaakteDrone), Drone.class)
                .retrieve()
                .bodyToMono(Drone.class)
                .block();

        System.out.println("\nDrone aangepast: " + aangepasteDrone);

        // ---- POST /api/drone/fly/{id} → drone laten vliegen ----

        // simulatie: een vlucht van 25 meter in turbo
        Map<String, Object> flyPayload = new HashMap<>();
        flyPayload.put("distance", 25);
        flyPayload.put("strategy", "turboModeStrategy");

        String batterijNaVlucht = webClient
                .post()
                .uri("/fly/" + aangepasteDrone.getDroneID())
                .bodyValue(flyPayload)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("\nBatterij na vlucht: " + batterijNaVlucht);

        // ---- POST /api/drone/status/{id} → drone status wijzigen ----

        Map<String, String> statusPayload = new HashMap<>();
        statusPayload.put("status", "MAINTENANCE");

        String responseStatus = webClient
                .post()
                .uri("/status/" + aangepasteDrone.getDroneID())
                .bodyValue(statusPayload)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("\nStatus aangepast: " + responseStatus);

        // ---- DELETE /api/drone/{id} → drone verwijderen ----
        webClient
                .delete()
                .uri("/" + aangepasteDrone.getDroneID())
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        System.out.println("\nDrone verwijderd: " + aangepasteDrone.getDroneID());

        System.out.println("________________________");
    }
}
