package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entité représentant un article (post) publié par un utilisateur dans l'application.
 * <p>
 * Chaque article possède un titre, un contenu, un auteur (utilisateur), un thème (topic) et une date de création.
 * La date de création est automatiquement renseignée lors de l'enregistrement grâce à l'auditing.
 * </p>
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity author;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TopicEnum topic;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

}
