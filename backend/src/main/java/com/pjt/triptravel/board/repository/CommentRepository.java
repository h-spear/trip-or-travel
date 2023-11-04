package com.pjt.triptravel.board.repository;

import java.util.Optional;

import com.pjt.triptravel.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByIdAndCommenterId(Long id, Long commenterId);
	Optional<Comment> findByIdAndPostId(Long id, Long postId);
}
