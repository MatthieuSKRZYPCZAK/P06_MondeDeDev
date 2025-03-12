package com.openclassrooms.mddapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static com.openclassrooms.mddapi.common.ApiRoutes.LOGIN_URL;
import static com.openclassrooms.mddapi.common.ApiRoutes.REGISTER_URL;

@Configuration
public class SpringSecurityConfig {


	/**
	 * Configure la protection des endpoints avec JWT.
	 *
	 * @param http Configuration de la sécurité HTTP.
	 * @return Un filtre de sécurité configuré.
	 * @throws Exception Si une erreur survient.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								REGISTER_URL,
								LOGIN_URL
						).permitAll() // Routes publiques
						.anyRequest().authenticated() // Tout le reste nécessite un JWT
				)
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter()))
				);

		return http.build();
	}

	/**
	 * Configure un convertisseur pour extraire les rôles du token JWT.
	 *
	 * @return Un convertisseur de JWT pour l'authentification.
	 */
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		return new JwtAuthenticationConverter();
	}

	/**
	 * Fournit un gestionnaire d'authentification.
	 *
	 * @param authConfig Configuration de l'authentification.
	 * @return Un `AuthenticationManager` pour gérer l'authentification.
	 * @throws Exception Si une erreur survient.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}


}
