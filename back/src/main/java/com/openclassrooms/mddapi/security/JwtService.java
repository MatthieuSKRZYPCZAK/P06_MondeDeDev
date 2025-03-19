package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.exception.InvalidJwtException;
import com.openclassrooms.mddapi.model.UserEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.openclassrooms.mddapi.common.ApiRoutes.JWT_REFRESH_URL;
import static com.openclassrooms.mddapi.common.ResponseMessages.INVALID_JWT;

@Service
public class JwtService {

	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationInMs;

	@Value("${app.jwtRefreshExpirationMs}")
	private int jwtRefreshExpirationInMs;

	@Autowired
	public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
	}

	/**
	 * Génère un token JWT pour un utilisateur à partir de son email.
	 *
	 * @param user l'object userEntity.
	 * @return Un token JWT signé.
	 */
	public String generateToken(UserEntity user) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(user.getEmail())
				.issuedAt(now)
				.expiresAt(now.plusMillis(jwtExpirationInMs))
				.build();

		return this.jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
	}

	public void generateAndSetRefreshToken(UserEntity user, HttpServletResponse response) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(user.getEmail())
				.issuedAt(now)
				.expiresAt(now.plusMillis(jwtRefreshExpirationInMs))
				.build();

		String refreshToken = this.jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();

		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setPath(JWT_REFRESH_URL);
		refreshTokenCookie.setMaxAge(jwtRefreshExpirationInMs / 1000);

		response.addCookie(refreshTokenCookie);
	}

	/**
	 * Supprime le refresh token du navigateur.
	 */
	public void clearRefreshToken(HttpServletResponse response) {
		Cookie deleteCookie = new Cookie("refreshToken", null);
		deleteCookie.setHttpOnly(true);
		deleteCookie.setSecure(true);
		deleteCookie.setPath(JWT_REFRESH_URL);
		deleteCookie.setMaxAge(0); // Expiration immédiate

		response.addCookie(deleteCookie);
	}

	public String extractEmailFromToken(String token) {
		Jwt decodedJwt = jwtDecoder.decode(token);
		return decodedJwt.getSubject();
	}
}
