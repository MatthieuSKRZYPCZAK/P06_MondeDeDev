package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un utilisateur de l'application.
 * <p>
 * Chaque utilisateur possède :
 * <ul>
 *     <li>un nom d'utilisateur unique ({@code username}),</li>
 *     <li>une adresse e-mail unique et valide ({@code email}),</li>
 *     <li>un mot de passe sécurisé ({@code password}),</li>
 *     <li>un ensemble de thèmes abonnés ({@code topics}),</li>
 *     <li>des dates d'audit de création ({@code createdAt}) et de dernière modification ({@code updatedAt}).</li>
 * </ul>
 * Les contraintes de validation sont appliquées sur les champs critiques pour garantir l'intégrité des données.
 * </p>
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "Username cannot be blank")
	@Size(min = 3, max = 20, message = "Name must be between {min} and {max} characters")
	private String username;

	@Column(unique = true, nullable = false)
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email cannot be blank")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 128, message = "Password must be between {min} and {max} characters")
	private String password;

	@ElementCollection(targetClass = TopicEnum.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "subscriptions", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Set<TopicEnum> topics = new HashSet<>();

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
