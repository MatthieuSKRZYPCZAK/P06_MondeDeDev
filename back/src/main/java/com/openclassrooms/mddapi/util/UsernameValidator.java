package com.openclassrooms.mddapi.util;


import org.springframework.stereotype.Component;

@Component
public class UsernameValidator {

	/**
	 * Expression régulière utilisée pour valider le format du nom d'utilisateur.
	 * <p>
	 * <b>Règles appliquées :</b>
	 * <ul>
	 *   <li><b>Doit contenir entre 3 et 20 caractères.</b></li>
	 *   <li><b>Peut contenir des lettres majuscules et/ou minuscules.</b></li>
	 *   <li><b>Peut contenir des chiffres.</b></li>
	 *   <li><b>Ne doit pas contenir d'espaces ni de caractères spéciaux.</b></li>
	 *   <li><b>Ne doit pas contenir plus de 5 chiffres consécutifs.</b></li>
	 *   <li><b>Doit contenir au moins 3 lettres.</b></li>
	 * </ul>
	 *
	 * <p><b>Explication de la regex :</b></p>
	 * <pre>
	 * ^(?!.*\d{6,})[a-zA-Z0-9]{3,20}$
	 * </pre>
	 * <ul>
	 *   <li><code>^</code> → Début de la chaîne.</li>
	 *   <li><code>(?!.*\d{6,})</code> → Vérifie qu'il <b>n'y a pas plus de 5 chiffres consécutifs</b>.</li>
	 *   <li><code>[a-zA-Z0-9]{3,20}</code> → Accepte uniquement <b>des lettres et des chiffres</b>, avec une longueur de 3 à 20 caractères.</li>
	 *   <li><code>$</code> → Fin de la chaîne.</li>
	 * </ul>
	 */
	private static final String USERNAME_PATTERN = "^(?!.*\\d{6,})[a-zA-Z0-9]{3,20}$";

	/**
	 * Vérifie si un nom d'utilisateur est valide selon les critères définis.
	 *
	 * @param username Le nom d'utilisateur à vérifier.
	 * @return {@code true} si le nom d'utilisateur est valide, sinon {@code false}.
	 */
	public boolean isValidUsername(String username) {
		if(!username.matches(USERNAME_PATTERN)) {
			return false;
		}

		// Vérification que le pseudo contient au moins 3 lettres
		long letterCount = username.chars().filter(Character::isLetter).count();
		return letterCount >= 3;
	}
}
