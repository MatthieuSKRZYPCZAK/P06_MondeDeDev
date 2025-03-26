package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper permettant de convertir entre les entités de type CommentEntity et leurs représentations DTO.
 */
@Component
public class CommentMapper {

	private final UserMapper userMapper;


	public CommentMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/**
	 * Convertit une entité CommentEntity en CommentDTO.
	 *
	 * @param comment L'entité représentant un commentaire.
	 * @return L'objet DTO correspondant au commentaire.
	 */
	public CommentDTO toCommentDTO(CommentEntity comment) {
		return new CommentDTO(
				comment.getId(),
				comment.getContent(),
				comment.getCreatedAt(),
				userMapper.toUserDTO(comment.getAuthor())
		);
	}

	/**
	 * Crée une entité CommentEntity à partir d'un CommentCreateDTO.
	 *
	 * @param dto    Le DTO contenant les données du commentaire à créer.
	 * @param author L'utilisateur auteur du commentaire.
	 * @param post   L'article auquel le commentaire est associé.
	 * @return Une nouvelle entité CommentEntity initialisée.
	 */
	public CommentEntity fromCreateDTO(CommentCreateDTO dto, UserEntity author, PostEntity post) {
		CommentEntity comment = new CommentEntity();
		comment.setContent(dto.content());
		comment.setAuthor(author);
		comment.setPost(post);
		return comment;
	}
}
