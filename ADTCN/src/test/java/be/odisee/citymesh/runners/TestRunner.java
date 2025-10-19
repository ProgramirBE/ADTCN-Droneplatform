package be.odisee.citymesh.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * De {@code TestRunner} klasse is verantwoordelijk voor het starten van
 * de Cucumber/Serenity BDD-testscenario's.
 *
 * <p>Deze runner gebruikt {@link CucumberWithSerenity} als test engine
 * om integratie met het Serenity-framework mogelijk te maken voor betere
 * rapportage en traceerbaarheid van BDD-tests.</p>
 *
 * <p>Configuratieopties:
 * <ul>
 *     <li><b>features</b>: het pad naar de Gherkin-featurebestanden</li>
 *     <li><b>glue</b>: de Java-package waar de stepdefinitions zich bevinden</li>
 * </ul>
 * </p>
 *
 * @see CucumberWithSerenity
 * @see io.cucumber.junit.CucumberOptions
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "be.odisee"
)
public class TestRunner {
}
