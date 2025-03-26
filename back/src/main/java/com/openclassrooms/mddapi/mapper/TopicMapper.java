package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.TopicEnum;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion d'une énumération TopicEnum
 * en objet de transfert de données (DTO) TopicDTO.
 */
@Component
public class TopicMapper {

	/**
	 * Convertit une valeur de l'énumération TopicEnum en un objet TopicDTO.
	 *
	 * @param topic Le sujet à convertir, représenté par l'énumération TopicEnum.
	 * @return L'objet TopicDTO correspondant au sujet.
	 */
	public TopicDTO toTopicDTO(TopicEnum topic) {
		return new TopicDTO(
				topic.name(),
				topic.getLabel(),
				topic.getDescription()
		);
	}
}
