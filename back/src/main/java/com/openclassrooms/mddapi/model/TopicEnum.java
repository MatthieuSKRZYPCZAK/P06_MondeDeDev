package com.openclassrooms.mddapi.model;

import lombok.Getter;

@Getter
public enum TopicEnum {
	JAVA("Java", "Object-oriented programming language."),
	JAVASCRIPT("JavaScript", "Language for web development."),
	PYTHON("Python", "Popular high-level language used in web, data science, and automation."),
	ANGULAR("Angular", "Frontend framework by Google."),
	REACT("React", "JavaScript library for building UIs."),
	NODEJS("Node.js", "JavaScript runtime for server-side apps."),
	SPRINGBOOT("SpringBoot", "Java framework for building web applications."),
	MYSQL("MySQL", "Relational database management system."),
	POSTGRESQL("PostgreSQL", "Advanced open source SQL database."),
	AI("AI", "Artificial Intelligence and Machine Learning."),
	ASPNET("ASP.NET", "Microsoft framework for web apps."),
	CSS("CSS", "Stylesheet language for designing web pages."),
	PHP("PHP", "Server-side scripting language."),
	RUST("Rust", "Safe and fast systems programming language.");

	private final String label;
	private final String description;

	TopicEnum(String label, String description) {
		this.label = label;
		this.description = description;
	}

}
