INSERT IGNORE INTO users (id, username, email, password, created_at) VALUES
                                                                          (900101, 'johnDoe', 'john@example.com', '$2a$10$k307GRbFIB9IW1PcwtbjjOcQ4rLfvKIDkz5ruxOUEKteuShYRk3.2', '2025-01-01 14:27:00'),
                                                                          (900102,'janeDoe', 'jane@example.com', '$2a$10$tjy31HIaxyQz8K75TD248.61dsJbqk62a0g3mUIUkGQvNJ4B.xFi2', '2025-02-11 07:44:00'),
                                                                          (900103,'adminUser', 'admin@example.com', '$2a$10$w/Zm4PuIORcEbm545F8ypuaKSlCNMKleCwX1HYL3cJCTGjZ37sSxW', '2024-12-04 10:21:00');

INSERT IGNORE INTO posts (id, title, content, topic, user_id, created_at) VALUES
-- Java (topic_id 1)
(900201, 'Introduction à Java', 'Java est un langage de programmation orienté objet créé par James Gosling en 1995. Il est connu pour son principe "Write Once, Run Anywhere".', 'JAVA', 900101, '2025-04-01 10:30:00'),
(900202, 'Les nouveautés de Java 17', 'Java 17 apporte de nombreuses améliorations comme les sealed classes, les pattern matching et plus encore.', 'JAVA', 900102, '2025-03-01 16:38:00'),

-- JavaScript (topic_id 2)
(900203, 'JavaScript moderne avec ES6', 'ES6 a introduit des fonctionnalités majeures comme les arrow functions, les classes et les modules.', 'JAVASCRIPT', 900103, '2025-03-14 09:04:00'),
(900204, 'Async/Await en JS', 'Comment utiliser async/await pour simplifier le code asynchrone en JavaScript.', 'JAVASCRIPT', 900101, '2025-04-01 14:35:00'),
(900205, 'Les frameworks JS en 2023', 'Comparaison entre React, Angular et Vue.js pour choisir le bon framework.', 'JAVASCRIPT', 900102, '2025-03-01 22:30:00'),

-- Python (topic_id 3)
(900206, 'Pourquoi apprendre Python?', 'Python est simple, puissant et utilisé en data science, web et automation.', 'PYTHON', 900103, '2025-03-01 10:10:00'),
(900207, 'Les décorateurs en Python', 'Guide complet sur les décorateurs, un concept puissant de Python.', 'PYTHON', 900101, '2025-04-01 11:14:00'),

-- Angular (topic_id 4)
(900208, 'Angular vs React', 'Comparaison approfondie entre ces deux frameworks frontend populaires.', 'ANGULAR', 900102, '2025-02-01 15:40:00'),
(900209, 'Les Signals dans Angular 16', 'Nouvelle fonctionnalité de gestion d''état dans Angular 16.', 'ANGULAR', 900103, '2025-02-01 18:38:00'),

-- React (topic_id 5)
(900210, 'React Hooks expliqués', 'Comment utiliser les hooks pour simplifier vos composants React.', 'REACT', 900101, '2025-03-01 18:30:00'),
(900211, 'Optimisation des performances React', 'Techniques avancées pour améliorer les performances.', 'REACT', 900102, '2025-02-01 21:34:00'),

-- Node.js (topic_id 6)
(900212, 'Construire une API REST avec Node', 'Guide pas à pas pour créer une API moderne avec Express.', 'NODEJS', 900103, '2025-02-01 08:32:00'),
(900213, 'Gestion des erreurs en Node.js', 'Bonnes pratiques pour la gestion des erreurs dans les applications Node.', 'NODEJS', 900101, '2025-03-01 10:23:00'),

-- SpringBoot (topic_id 7)
(900214, 'Démarrer avec Spring Boot', 'Configuration initiale et création de votre premier projet.', 'SPRINGBOOT', 900102, '2025-02-01 09:04:00'),
(900215, 'Spring Security en pratique', 'Comment sécuriser votre application Spring Boot.', 'SPRINGBOOT', 900103, '2025-03-01 10:51:00'),

-- MySQL (topic_id 8)
(900216, 'Optimisation des requêtes MySQL', 'Indexation et techniques d''optimisation avancées.', 'MYSQL', 900101, '2025-04-01 10:40:00'),
(900217, 'Migration MySQL 5.7 vers 8.0', 'Guide complet pour une migration réussie.', 'MYSQL', 900102, '2025-04-01 10:38:00'),

-- PostgreSQL (topic_id 9)
(900218, 'Fonctionnalités avancées de PostgreSQL', 'CTE, fenêtrage et autres fonctionnalités puissantes.', 'POSTGRESQL', 900103, '2025-04-01 17:30:00'),
(900219, 'PostgreSQL vs MySQL', 'Comparaison approfondie entre ces deux SGBD populaires.', 'POSTGRESQL', 900101, '2025-04-01 18:32:00'),

-- AI (topic_id 10)
(900220, 'Introduction au Machine Learning', 'Concepts de base et cas d''utilisation pratiques.', 'AI', 900102, '2025-04-01 16:15:00'),
(900221, 'TensorFlow vs PyTorch', 'Quel framework choisir pour vos projets IA?', 'AI', 900103, '2025-03-01 15:48:00'),

-- ASP.NET (topic_id 11)
(900222, 'Débuter avec ASP.NET Core', 'Création de votre première application web.', 'ASPNET', 900101, '2025-04-01 16:44:00'),
(900223, 'Authentification dans ASP.NET', 'Implémentation d''un système d''auth sécurisé.', 'ASPNET', 900102, '2025-03-01 14:52:00'),

-- CSS (topic_id 12)
(900224, 'CSS Grid vs Flexbox', 'Quand utiliser chaque technique de mise en page.', 'CSS', 900103, '2025-04-21 18:30:00'),
(900225, 'Les animations CSS modernes', 'Création d''animations fluides sans JavaScript.', 'CSS', 900101, '2025-02-24 01:08:00'),

-- PHP (topic_id 13)
(900226, 'PHP 8: Les nouveautés', 'JIT compiler, named arguments et plus encore.', 'PHP', 900102, '2025-04-09 10:17:00'),
(900227, 'Laravel: Le framework PHP moderne', 'Pourquoi Laravel est un excellent choix en 2023.', 'PHP', 900103, '2025-03-24 10:22:00'),

-- Rust (topic_id 14)
(900228, 'Pourquoi Rust gagne en popularité?', 'Sécurité mémoire et performances sans compromis.', 'RUST', 900101, '2025-04-11 12:47:00'),
(900229, 'Rust pour le web: Actix vs Rocket', 'Comparaison des frameworks web Rust.', 'RUST', 900102, '2025-02-28 17:38:00');

