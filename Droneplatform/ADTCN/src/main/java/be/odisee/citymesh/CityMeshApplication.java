package be.odisee.citymesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("be.odisee.citymesh.entity")
@EnableJpaRepositories("be.odisee.citymesh.repository")
public class CityMeshApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityMeshApplication.class, args);
    }
}

