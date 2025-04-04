package com.openclassrooms.mddapi.model;

import lombok.Getter;

@Getter
public enum TopicEnum {
	JAVA("Java", "A powerful, versatile object-oriented programming language widely used for building scalable applications, especially in enterprise environments."),
	JAVASCRIPT("JavaScript", "A dynamic scripting language essential for web development, enabling interactive web pages and complex front-end functionalities."),
	PYTHON("Python", "A highly versatile and readable programming language, extensively used in web development, data science, artificial intelligence, automation, and scripting."),
	ANGULAR("Angular", "A robust and comprehensive frontend web framework developed by Google, commonly used to build scalable, dynamic single-page applications."),
	REACT("React", "An efficient, component-based JavaScript library maintained by Facebook, widely adopted for building interactive and modular user interfaces."),
	NODEJS("Node.js", "A powerful JavaScript runtime environment built on Chrome's V8 engine, enabling efficient server-side scripting and scalable backend applications."),
	SPRINGBOOT("SpringBoot", "A widely-used Java framework that simplifies the development of robust, production-grade web applications and microservices."),
	MYSQL("MySQL", "An open-source relational database management system popular for its simplicity, reliability, and high performance in web applications."),
	POSTGRESQL("PostgreSQL", "A sophisticated, open-source relational database system renowned for advanced features, reliability, and standards compliance in complex applications."),
	AI("AI", "Artificial Intelligence encompasses technologies such as machine learning, deep learning, and neural networks, enabling systems to learn, reason, and solve problems autonomously."),
	ASPNET("ASP.NET", "A comprehensive web development framework by Microsoft, facilitating the creation of modern, high-performance web applications and services using .NET."),
	CSS("CSS", "A styling language crucial for designing and customizing the layout, appearance, and responsiveness of web pages across browsers and devices."),
	PHP("PHP", "A widely-used server-side scripting language designed specifically for web development, known for powering dynamic content, websites, and web applications."),
	RUST("Rust", "A modern systems programming language renowned for its memory safety, performance, and concurrency features, widely adopted for building safe and efficient applications.");

	private final String label;
	private final String description;

	TopicEnum(String label, String description) {
		this.label = label;
		this.description = description;
	}

}
