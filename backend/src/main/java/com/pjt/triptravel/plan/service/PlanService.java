package com.pjt.triptravel.plan.service;

import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.attraction.repository.AttractionRepository;
import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;
import com.pjt.triptravel.plan.dto.*;
import com.pjt.triptravel.plan.entity.Plan;
import com.pjt.triptravel.plan.entity.PlanItem;
import com.pjt.triptravel.plan.repository.PlanItemRepository;
import com.pjt.triptravel.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final PlanItemRepository planItemRepository;
    private final AttractionRepository attractionRepository;

    public List<PlanSimpleDto> getPlans(Long memberId) {
        return planRepository.findAllByMemberId(memberId)
                .stream().map(PlanSimpleDto::of)
                .collect(Collectors.toList());
    }

    public PlanDto getDetail(Long memberId, Long planId) {
        Plan plan = planRepository.findByIdWithDetail(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행 계획 번호입니다."));
        validatePlanWriterAndMember(plan, memberId);
        return PlanDto.of(plan);
    }

    @Transactional
    public void modify(Long memberId, Long planId, PlanUpdateParam param) {
        Plan plan = planRepository.findByIdWithItems(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행 계획 번호입니다."));
        validatePlanWriterAndMember(plan, memberId);

        deleteAllPlanItems(plan);
        planItemRepository.saveAll(
                getPlanItems(param.getPlanItems(), plan));

        plan.changePlan(param.getTitle(), param.getDescription(),
                        param.getStartDateTime(), param.getEndDateTime());
    }

    @Transactional
    public void delete(Long memberId, Long planId) {
        Plan plan = planRepository.findByIdWithItems(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행 계획 번호입니다."));
        validatePlanWriterAndMember(plan, memberId);

        deleteAllPlanItems(plan);
        planRepository.delete(plan);
    }

    @Transactional
    public Long createPlan(Long memberId, PlanCreateParam param) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);

        Plan plan = Plan.builder()
                .title(param.getTitle())
                .description(param.getDescription())
                .startDateTime(param.getStartDateTime())
                .endDateTime(param.getEndDateTime())
                .member(member)
                .build();
        Plan savedPlan = planRepository.save(plan);

        planItemRepository.saveAll(
                getPlanItems(param.getPlanItems(), plan));
        return savedPlan.getId();
    }

    private void deleteAllPlanItems(Plan plan) {
        List<Long> planItemIds = plan.getPlanItems().stream()
                .map(PlanItem::getId)
                .collect(Collectors.toList());
        planItemRepository.deleteAllById(planItemIds);
    }

    private List<PlanItem> getPlanItems(List<PlanItemParam> itemParams, Plan plan) {
        List<Long> attractionIds = itemParams
                .stream().map(PlanItemParam::getAttractionId)
                .collect(Collectors.toList());

        Map<Long, AttractionInfo> attractionIdMap = attractionRepository.findAllById(attractionIds)
                .stream()
                .collect(Collectors.toMap(
                        AttractionInfo::getId,
                        attraction -> attraction));

        List<PlanItem> planItems = new ArrayList<>(itemParams.size());
        itemParams.forEach(itemParam -> planItems.add(PlanItem.builder()
                .attractionInfo(attractionIdMap.get(itemParam.getAttractionId()))
                .startDateTime(itemParam.getStartDateTime())
                .endDateTime(itemParam.getEndDateTime())
                .memo(itemParam.getMemo())
                .plan(plan)
                .order(itemParam.getOrder())
                .build()));
        return planItems;
    }

    private void validatePlanWriterAndMember(Plan plan, Long memberId) {
        if (plan.getMember() == null || !plan.getMember().getId().equals(memberId))
            throw new IllegalArgumentException("여행 계획에 접근할 수 없습니다.");
    }
}
