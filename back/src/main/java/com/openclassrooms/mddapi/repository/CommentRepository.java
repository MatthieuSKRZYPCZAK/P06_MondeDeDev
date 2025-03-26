package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findByPost(PostEntity post);
}
