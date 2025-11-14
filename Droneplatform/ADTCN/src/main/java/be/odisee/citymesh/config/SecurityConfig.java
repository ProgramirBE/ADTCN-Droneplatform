package be.odisee.citymesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // This configures security for your API endpoints and the login form
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Your API endpoints MUST be authenticated
                        .antMatchers("/api/**").authenticated().anyRequest().permitAll()
                        // All other requests (like /login, /oauth2/authorize) are permitted
                        .anyRequest().permitAll()
                )
                // Use the default Spring Security login form
                .formLogin(Customizer.withDefaults())
                // Configure as a Resource Server, accepting JWTs (Access Tokens)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    // These are the test users from your original app.vue
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails pilot = User.builder()
                .username("pilot1")
                .password(encoder.encode("pilot1pass"))
                .roles("PILOT")
                .build();
        UserDetails mechanic = User.builder()
                .username("mech1")
                .password(encoder.encode("mech1pass"))
                .roles("MECHANIC") // You can map roles to OIDC scopes or claims later
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("adminpass"))
                .roles("ADMIN", "PILOT", "MECHANIC")
                .build();

        return new InMemoryUserDetailsManager(pilot, mechanic, admin);
    }
}