package com.pjt.triptravel.board.repository;

import java.util.List;
import java.util.Optional;

import com.pjt.triptravel.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByIdAndCommenterId(Long id, Long commenterId);
	Optional<Comment> findByIdAndPostId(Long id, Long postId);

	@Query("select distinct c from Comment c "
		+ " left join fetch c.children ch "
		+ " join fetch c.commenter "
		+ " where c.post.id = :postId")
	List<Comment> findByPostIdWithCommenter(@Param("postId") Long postId);
}
