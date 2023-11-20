package com.pjt.triptravel.attraction.repository.region;

import com.pjt.triptravel.attraction.entity.region.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SidoRepository extends JpaRepository<Sido, Long> {

    @Query("select distinct s from Sido s join fetch s.guguns g")
    List<Sido> findAllWithGugun();
}
