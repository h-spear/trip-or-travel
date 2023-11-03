package com.pjt.triptravel.board.repository;

import com.pjt.triptravel.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
