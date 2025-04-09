package com.openclassrooms.mddapi.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de l'OpenAPI pour l'application Monde de Dév.
 * Définit :
 * - Les informations générales de l'API (titre, version, description, contact).
 * - Le serveur de base (localhost:8080).
 * - Le schéma de sécurité JWT pour l'authentification Bearer.
 */
@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Monde de Dév",
				version = "1.0",
				description = "This API allows managing MDD",
				contact = @Contact(
						name = "Matthieu"
				)
		),
		servers = @Server(url = "http://localhost:8080")
)
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
public class OpenApiConfig {
}
