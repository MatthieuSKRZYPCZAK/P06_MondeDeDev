package com.openclassrooms.mddapi.model;

import lombok.Getter;

@Getter
public enum TopicEnum {
	JAVA("Java", "Un langage de programmation orienté objet puissant et polyvalent, largement utilisé pour créer des applications évolutives, particulièrement dans les environnements d'entreprise."),
	JAVASCRIPT("JavaScript", "Un langage de script dynamique essentiel au développement web, permettant de créer des pages interactives et des fonctionnalités front-end complexes."),
	PYTHON("Python", "Un langage de programmation très polyvalent et lisible, largement utilisé en développement web, science des données, intelligence artificielle, automatisation et scripting."),
	ANGULAR("Angular", "Un framework frontend robuste et complet développé par Google, couramment utilisé pour construire des applications monopages dynamiques et évolutives."),
	REACT("React", "Une bibliothèque JavaScript efficace et basée sur les composants, maintenue par Facebook, très populaire pour créer des interfaces utilisateur interactives et modulaires."),
	NODEJS("Node.js", "Un environnement d'exécution JavaScript performant basé sur le moteur V8 de Chrome, permettant de développer des applications backend scalables et des scripts côté serveur."),
	SPRINGBOOT("SpringBoot", "Un framework Java très utilisé qui simplifie le développement d'applications web robustes et de qualité professionnelle, ainsi que des microservices."),
	MYSQL("MySQL", "Un système de gestion de base de données relationnelle open-source, réputé pour sa simplicité, sa fiabilité et ses performances dans les applications web."),
	POSTGRESQL("PostgreSQL", "Un système de base de données relationnelle open-source sophistiqué, reconnu pour ses fonctionnalités avancées, sa fiabilité et sa conformité aux standards dans les applications complexes."),
	AI("AI", "L'intelligence artificielle englobe des technologies comme le machine learning, le deep learning et les réseaux neuronaux, permettant aux systèmes d'apprendre, de raisonner et de résoudre des problèmes de manière autonome."),
	ASPNET("ASP.NET", "Un framework complet de développement web créé par Microsoft, facilitant la création d'applications et services web modernes et performants avec .NET."),
	CSS("CSS", "Un langage de style essentiel pour concevoir et personnaliser la mise en page, l'apparence et la réactivité des sites web sur différents navigateurs et appareils."),
	PHP("PHP", "Un langage de script côté serveur très utilisé, spécialement conçu pour le développement web, connu pour alimenter des sites dynamiques et des applications web."),
	RUST("Rust", "Un langage de programmation système moderne réputé pour sa sécurité mémoire, ses performances et ses fonctionnalités de concurrence, souvent utilisé pour créer des applications sûres et efficaces.");

	private final String label;
	private final String description;

	TopicEnum(String label, String description) {
		this.label = label;
		this.description = description;
	}

}
