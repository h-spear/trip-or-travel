package com.pjt.triptravel.attraction.repository;

import com.pjt.triptravel.attraction.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttractionRepository extends JpaRepository<AttractionInfo, Long> {

    @Query("select a from AttractionInfo a join fetch a.description d where a.id = :id")
    Optional<AttractionInfo> findByIdWithDescription(@Param("id") Long id);
}
