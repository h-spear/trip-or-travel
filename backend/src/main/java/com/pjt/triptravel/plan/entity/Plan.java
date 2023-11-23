package com.pjt.triptravel.plan.entity;

import com.pjt.triptravel.common.entity.BaseEntity;
import com.pjt.triptravel.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Plan extends BaseEntity {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @OneToMany(mappedBy = "plan")
    private List<PlanItem> planItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Plan() {
    }

    @Builder
    public Plan(String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Member member) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        setMember(member);
    }

    public void changePlan(String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void setMember(Member member) {
        this.member = member;
        member.getPlans().add(this);
    }
}
