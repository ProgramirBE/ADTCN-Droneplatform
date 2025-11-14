package be.odisee.citymesh.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class AuthorizationServerConfig {
    // This configures the OIDC-specific endpoints like /oauth2/authorize and /oauth2/token
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();
        http.apply(authorizationServerConfigurer);

        http
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                );

        return http.build();
    }

    // This defines your Vue.js frontend as a "client" that is allowed to ask for authentication
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // This client is based on your PDF's instructions (slide 17)
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("my-client") // As seen in PDF slide 17
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // Public client
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost/callback") // Nginx will run on port 80
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("api_access") // A custom scope for your API
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(true) // Enforce PKCE
                        .requireAuthorizationConsent(false) // Skip consent screen for dev
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(oidcClient);
    }

    // This configures the "issuer" URL, which is the base URL of your auth server
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        // Nginx will proxy to this server, so from the outside, the issuer is localhost
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost")
                .build();
    }
}
