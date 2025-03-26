package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.TopicEnum;
import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.openclassrooms.mddapi.common.ResponseMessages.TOPIC_NOT_FOUND;

/**
 * Mapper permettant de convertir entre les entités de type PostEntity et leurs représentations DTO.
 */
@Component
public class PostMapper {

	private final UserMapper userMapper;
	private final TopicMapper topicMapper;
	private final CommentMapper commentMapper;

	public PostMapper(UserMapper userMapper, TopicMapper topicMapper, CommentMapper commentMapper) {
		this.userMapper = userMapper;
		this.topicMapper = topicMapper;
		this.commentMapper = commentMapper;
	}

	/**
	 * Convertit un PostCreateDTO en entité PostEntity.
	 *
	 * @param dto    Le DTO contenant les données de création de post.
	 * @param author L'auteur du post (utilisateur authentifié).
	 * @return L'entité PostEntity correspondante.
	 * @throws TopicNotFoundException si le nom du topic fourni est invalide.
	 */
	public PostEntity fromPostCreateDto(PostCreateDTO dto, UserEntity author) {
		TopicEnum topic;
		try {
			topic = TopicEnum.valueOf(dto.topicName().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new TopicNotFoundException(String.format(TOPIC_NOT_FOUND, dto.topicName()));
		}

		PostEntity post = new PostEntity();
		post.setTitle(dto.title());
		post.setContent(dto.content());
		post.setAuthor(author);
		post.setTopic(topic);

		return post;
	}

	/**
	 * Convertit une entité PostEntity en PostDTO sans commentaires.
	 *
	 * @param post L'entité PostEntity à convertir.
	 * @return Le DTO PostDTO correspondant.
	 */
	public PostDTO toPostDto(PostEntity post) {
		return buildPostDTO(post, List.of());
	}

	/**
	 * Convertit une entité PostEntity en PostDTO avec une liste de commentaires.
	 *
	 * @param post      L'entité PostEntity à convertir.
	 * @param comments  La liste des entités CommentEntity associées au post.
	 * @return Le DTO PostDTO enrichi avec les commentaires.
	 */
	public PostDTO toPostDto(PostEntity post, List<CommentEntity> comments) {
		List<CommentDTO> commentDTOs = comments.stream()
				.map(commentMapper::toCommentDTO)
				.toList();

		return buildPostDTO(post, commentDTOs);
	}

	/**
	 * Méthode utilitaire privée pour construire un PostDTO.
	 *
	 * @param post      L'entité PostEntity.
	 * @param comments  Les commentaires associés (convertis en DTO).
	 * @return Le DTO PostDTO final.
	 */
	private PostDTO buildPostDTO(PostEntity post, List<CommentDTO> comments) {
		return new PostDTO(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				topicMapper.toTopicDTO(post.getTopic()),
				userMapper.toUserDTO(post.getAuthor()),
				post.getCreatedAt(),
				comments
		);
	}
}