package be.odisee.citymesh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Belangrijk voor @PreAuthorize/@PostAuthorize
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        // CSRF uitschakelen voor H2 console en API endpoints.
                        // Voor API's is dit nodig als JS fetch geen CSRF-token meestuurt.
                        // In productie wil je hier over nadenken voor een betere beveiliging.
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2/**"))
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"))
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame
                                .sameOrigin() // Nodig voor H2 console in een iframe
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        // Pagina's en resources die iedereen mag zien (publiek)
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),            // Root URL (kan doorsturen naar /login of /drones)
                                new AntPathRequestMatcher("/login*"),       // Login pagina
                                new AntPathRequestMatcher("/h2/**"),        // H2 console
                                new AntPathRequestMatcher("/h2-console/**"),// H2 console exact path
                                new AntPathRequestMatcher("/api/**"),       // Autoriseer REST API endpoints
                                new AntPathRequestMatcher("/favicon.ico"),  // Favicon
                                new AntPathRequestMatcher("/images/**"),    // Afbeeldingen
                                new AntPathRequestMatcher("/css/**"),       // CSS bestanden
                                new AntPathRequestMatcher("/js/**"),        // JavaScript bestanden
                                new AntPathRequestMatcher("/access-denied") // Pagina voor toegang geweigerd
                        ).permitAll()
                        // Alle andere verzoeken vereisen authenticatie (gebruiker moet ingelogd zijn)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // De URL van je custom login pagina
                        .failureUrl("/login?error=true") // URL bij mislukte login
                        .defaultSuccessUrl("/drones", true) // Waar naartoe na succesvolle login
                        .permitAll() // Zorgt ervoor dat iedereen de login formulier URL mag bezoeken
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true) // Maakt de sessie ongeldig
                        .logoutSuccessUrl("/login?logout=true") // Waar naartoe na logout
                        .permitAll() // Zorgt ervoor dat iedereen de logout URL mag bezoeken
                )
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler((request, response, ex) -> {
                            response.sendRedirect("/access-denied"); // Redirect bij 403 (toegang geweigerd)
                        })
                );

        return http.build();
    }

    /**
     * Definieert een {@link UserDetailsService} met twee in-memory gebruikers:
     * een gewone gebruiker en een admin.
     *
     * @return een {@link InMemoryUserDetailsManager} met de gebruikersgegevens
     */
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();

        // Standaardgebruiker met rol USER
        UserDetails user = User.builder()
                .username("citymeshuser")
                .password(encoder.encode("citymeshpwd"))
                .roles("USER")
                .build();

        // Admingebruiker met rollen ADMIN en USER
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("adminpwd"))
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * Biedt een password encoder aan volgens de standaard Spring Security-mechanismen.
     *
     * @return een {@link PasswordEncoder} die meerdere encoding-methoden ondersteunt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Maakt integratie tussen Thymeleaf en Spring Security mogelijk
     * zodat beveiligingsattributen (zoals sec:authorize) werken in templates.
     *
     * @return een {@link SpringSecurityDialect} voor Thymeleaf
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}