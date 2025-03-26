package com.openclassrooms.mddapi.common;

/**
 * Classe utilitaire contenant les routes de l'API utilisées dans l'application MDD.
 * Elle regroupe les URL des différents points d'entrée de l'API.
 * @author Matthieu SKRZYPCZAK
 */
public class ApiRoutes {

	/** URL de base de l'API */
	public static final String BASE_URL = "/api";


	/* =========================
	 *         AUTH
	 * ========================= */

	/** URL de base pour les routes d'authentification */
	public static final String AUTH_URL = BASE_URL+"/auth";

	/** URL de connexion (login) */
	public static final String LOGIN_URL = AUTH_URL+"/login";

	/** URL d'inscription (register) */
	public static final String REGISTER_URL = AUTH_URL+"/register";

	/** URL pour récupérer les informations de l'utilisateur connecté */
	public static final String ME_URL = AUTH_URL+"/me";

	/** URL pour rafraîchir le token JWT */
	public static final String JWT_REFRESH_URL = AUTH_URL+"/refresh";


	/* =========================
	 *         UTILISATEURS
	 * ========================= */

	/** URL de base pour les opérations utilisateurs */
	public static final String USER_URL = BASE_URL+"/user";

	/** URL pour la mise à jour des informations utilisateur */
	public static final String USER_UPDATE_URL = USER_URL+"/update";

	/** URL pour la mise à jour du mot de passe utilisateur */
	public static final String USER_PASSWORD_URL = USER_URL+"/password";

	/** URL pour s’abonner à un sujet */
	public static final String USER_SUBSCRIBE_TOPIC_URL = USER_URL+"/subscribe/{topicName}";

	/** URL pour se désabonner d’un sujet*/
	public static final String USER_UNSUBSCRIBE_TOPIC_URL = USER_URL+"/unsubscribe/{topicName}";

	/* =========================
	 *         SUJETS
	 * ========================= */

	/** URL pour accéder à la liste des sujets (topics) */
	public static final String TOPIC_URL = BASE_URL+"/topics";

	/* =========================
	 *         ARTICLES
	 * ========================= */

	/** URL de base pour les opérations sur les articles (posts) */
	public static final String POST_URL = BASE_URL+"/posts";

	/** URL pour accéder au fil d’actualités d’un utilisateur */
	public static final String POST_FEED_URL = POST_URL+"/feed";

	/** URL pour accéder au détail d’un article donné */
	public static final String POST_DETAIL_URL = POST_URL+"/{postId}";

	/* =========================
	 *         COMMENTAIRES
	 * ========================= */

	/** URL pour ajouter un commentaire à un article */
	public static final String POST_COMMENT_URL = POST_DETAIL_URL+"/comment";
}
