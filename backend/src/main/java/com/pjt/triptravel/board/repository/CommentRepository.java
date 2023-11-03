package com.pjt.triptravel.board.repository;

import com.pjt.triptravel.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
