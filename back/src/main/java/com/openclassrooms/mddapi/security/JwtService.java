package com.openclassrooms.mddapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

	private final JwtEncoder jwtEncoder;

	@Autowired
	public JwtService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	/**
	 * Génère un token JWT pour un utilisateur à partir de son email.
	 *
	 * @param authentication l'object authentication.
	 * @return Un token JWT signé.
	 */
	public String generateToken(Authentication authentication) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(authentication.getName())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(86400)) // Expiration après 24h
				.build();

		return this.jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
	}
}
