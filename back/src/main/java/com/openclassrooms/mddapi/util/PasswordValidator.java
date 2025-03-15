package com.openclassrooms.mddapi.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

	/**
	 * Vérifie si le mot de passe respecte les critères de sécurité suivants :
	 *
	 * <ul>
	 *   <li><b>Doit contenir au moins 8 caractères.</b></li>
	 *   <li><b>Doit inclure au moins une lettre minuscule</b> (<code>[a-z]</code>).</li>
	 *   <li><b>Doit inclure au moins une lettre majuscule</b> (<code>[A-Z]</code>).</li>
	 *   <li><b>Doit inclure au moins un chiffre</b> (<code>[0-9]</code>).</li>
	 *   <li><b>Doit inclure au moins un caractère spécial</b> (<code>@#$%^&+=</code>).</li>
	 *   <li><b>Ne doit pas contenir d'espaces.</b></li>
	 * </ul>
	 *
	 * <p><b>Explication de la regex :</b></p>
	 * <pre>
	 * ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
	 * </pre>
	 * <ul>
	 *   <li><code>(?=.*[0-9])</code> → Au moins un chiffre.</li>
	 *   <li><code>(?=.*[a-z])</code> → Au moins une lettre minuscule.</li>
	 *   <li><code>(?=.*[A-Z])</code> → Au moins une lettre majuscule.</li>
	 *   <li><code>(?=.*[@#$%^&+=])</code> → Au moins un caractère spécial parmi <code>@#$%^&+=</code>.</li>
	 *   <li><code>(?=\S+$)</code> → Aucun espace autorisé.</li>
	 *   <li><code>.{8,}$</code> → Minimum 8 caractères.</li>
	 * </ul>
	 *
	 * @param password Le mot de passe à valider.
	 * @return {@code true} si le mot de passe est valide, sinon {@code false}.
	 */
	public boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		return password.matches(regex);
	}
}
