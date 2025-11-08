package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_BRO;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/", glue = "be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_BRO.steps")
public class AcceptanceTestSuite {

}
