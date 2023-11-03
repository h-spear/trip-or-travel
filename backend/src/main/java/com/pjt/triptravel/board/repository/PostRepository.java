package com.pjt.triptravel.board.repository;

import com.pjt.triptravel.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
