package com.pjt.triptravel.board.repository;

import java.util.Optional;

import com.pjt.triptravel.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findByIdAndWriterId(Long id, Long writerId);
}
