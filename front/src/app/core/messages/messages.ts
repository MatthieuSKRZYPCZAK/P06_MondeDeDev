export const MESSAGES = {
  SERVICE_UNAVAILABLE: "Le service est actuellement indisponible",
  INVALID_CREDENTIALS: "Identifiants invalides",
  LOGOUT_SUCCESS: "Déconnexion réussie",
  LOGOUT_SESSION: "Votre session a expiré. Vous avez été déconnecté.",
  LOGIN_SUCCESS: "Connexion réussie",
  ERROR: "Une erreur est survenue",
  REGISTER_SUCCESS: "Inscription réussie ! Bienvenue 👋",
  SUBSCRIBE_SUCCESS: (theme: string) => `Vous êtes maintenant abonné au thème "${theme}"`,
  UNSUBSCRIBE_SUCCESS: (theme: string) => `Vous êtes désabonné du thème "${theme}"`,
  POST_CREATED_SUCCESS: "Article publié avec succès ! 🚀",
  COMMENT_SENT_SUCCESS: "Commentaire envoyé avec succès ! 💬",
};
