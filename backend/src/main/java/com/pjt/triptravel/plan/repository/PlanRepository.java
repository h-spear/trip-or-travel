package com.pjt.triptravel.plan.repository;

import com.pjt.triptravel.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
