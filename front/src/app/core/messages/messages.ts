export const MESSAGES = {

  // Connexion / Inscription
  SERVICE_UNAVAILABLE: "Le service est actuellement indisponible",
  INVALID_CREDENTIALS: "Identifiants invalides",
  LOGOUT_SUCCESS: "Déconnexion réussie",
  LOGOUT_SESSION: "Votre session a expiré. Vous avez été déconnecté.",
  LOGIN_SUCCESS: "Connexion réussie",
  ERROR: "Une erreur est survenue",
  REGISTER_SUCCESS: "Inscription réussie ! Bienvenue 👋",


  // Thèmes
  SUBSCRIBE_SUCCESS: (theme: string) => `Vous êtes maintenant abonné au thème "${theme}"`,
  UNSUBSCRIBE_SUCCESS: (theme: string) => `Vous êtes désabonné du thème "${theme}"`,

  // Articles
  POST_CREATED_SUCCESS: "Article publié avec succès ! 🚀",
  COMMENT_SENT_SUCCESS: "Commentaire envoyé avec succès ! 💬",

  // Utilisateur
  USERNAME_REQUIRED: "Le nom d'utilisateur est requis.",
  USERNAME_INVALID: "Le nom d'utilisateur doit contenir entre 3 et 20 caractères, uniquement lettres et chiffres.",
  USERNAME_ALREADY_EXISTS: "Ce nom d'utilisateur est déjà utilisé.",

  EMAIL_REQUIRED: "L'adresse e-mail est requise.",
  EMAIL_ALREADY_EXISTS: "Cette adresse e-mail est déjà utilisée.",
  EMAIL_INVALID: "Le format de l'adresse e-mail est invalide.",

  PASSWORD_REQUIRED: "Le mot de passe est requis.",
  PASSWORD_INVALID: "Le mot de passe doit avoir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.",
  PASSWORD_CONFIRMATION_ERROR: "Le mot de passe actuel est incorrect.",

  PROFILE_UPDATE_SUCCESS: "Votre profil a été mis à jour avec succès ! ✅",


};
