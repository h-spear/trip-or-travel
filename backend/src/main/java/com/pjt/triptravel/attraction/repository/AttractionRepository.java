package com.pjt.triptravel.attraction.repository;

import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.attraction.repository.custom.AttractionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttractionRepository extends JpaRepository<AttractionInfo, Long>, AttractionRepositoryCustom {

    @Query("select a from AttractionInfo a left join fetch a.description d where a.id = :id")
    Optional<AttractionInfo> findByIdWithDescription(@Param("id") Long id);
}
