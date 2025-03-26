package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.TopicEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
	List<PostEntity> findByTopicInOrderByCreatedAtDesc(Set<TopicEnum> subscribedTopics);
}
