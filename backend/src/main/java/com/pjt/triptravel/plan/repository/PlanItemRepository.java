package com.pjt.triptravel.plan.repository;

import com.pjt.triptravel.plan.entity.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanItemRepository extends JpaRepository<PlanItem, Long> {
}
