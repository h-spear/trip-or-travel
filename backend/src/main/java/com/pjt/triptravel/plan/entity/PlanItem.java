package com.pjt.triptravel.plan.entity;

import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Getter
public class PlanItem extends BaseEntity {

    @Id
    @Column(name = "plan_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private AttractionInfo attraction;
    private String memo;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Column(name = "plan_order")
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    protected PlanItem() {
    }

    @Builder
    public PlanItem(AttractionInfo attractionInfo, String memo, LocalDateTime startDateTime, LocalDateTime endDateTime, Plan plan, int order) {
        this.attraction = attractionInfo;
        this.memo = memo;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.order = order;
        setPlan(plan);
    }

    private void setPlan(Plan plan) {
        this.plan = plan;
        plan.getPlanItems().add(this);
    }
}
