package be.odisee.citymesh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller die verantwoordelijk is voor het afhandelen van loginverzoeken.
 *
 * <p>
 * Deze klasse behandelt GET-verzoeken naar de loginpagina en retourneert de naam
 * van de bijhorende view (bv. een Thymeleaf- of JSP-pagina genaamd "login").
 * </p>
 */
@Controller
public class LoginController {

    /**
     * Verwerkt GET-verzoeken naar de loginpagina.
     *
     * @return de naam van de loginview ("login")
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
