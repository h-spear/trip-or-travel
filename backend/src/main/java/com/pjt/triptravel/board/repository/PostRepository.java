package com.pjt.triptravel.board.repository;

import java.util.Optional;

import com.pjt.triptravel.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findByIdAndWriterId(Long id, Long writerId);

	@Query("select p from Post p left join fetch p.writer where p.id = :id")
	Optional<Post> findByIdWithWriter(@Param("id") Long id);
}
