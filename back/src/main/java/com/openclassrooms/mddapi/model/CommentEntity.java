package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entité représentant un commentaire associé à un article (Post) dans l'application.
 * <p>
 * Chaque commentaire est lié à un auteur (utilisateur) et à un article.
 * Lorsqu'un article est supprimé, tous ses commentaires sont également supprimés en cascade.
 * Le champ {@code createdAt} est automatiquement rempli lors de la création du commentaire grâce à l'auditing.
 * </p>
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class CommentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@ManyToOne(optional = false)
	@JoinColumn(name="user_id", nullable = false)
	private UserEntity author;

	@ManyToOne(optional = false)
	@JoinColumn(name="post_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private PostEntity post;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

}
