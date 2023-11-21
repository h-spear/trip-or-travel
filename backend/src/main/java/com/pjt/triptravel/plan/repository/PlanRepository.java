package com.pjt.triptravel.plan.repository;

import com.pjt.triptravel.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findAllByMemberId(Long memberId);

    @Query("select p from Plan p " +
            "join fetch p.member m " +
            "join fetch p.planItems pi " +
            "join fetch pi.attraction a " +
            "left join fetch a.contentType " +
            "where p.id = :id")
    Optional<Plan> findByIdWithDetail(@Param("id") Long id);

    @Query("select p from Plan p " +
            "join fetch p.member m " +
            "join fetch p.planItems pi " +
            "where p.id = :id")
    Optional<Plan> findByIdWithItems(@Param("id") Long id);
}
