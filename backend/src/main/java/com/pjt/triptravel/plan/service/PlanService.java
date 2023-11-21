package com.pjt.triptravel.plan.service;

import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.attraction.repository.AttractionRepository;
import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;
import com.pjt.triptravel.plan.dto.PlanCreateParam;
import com.pjt.triptravel.plan.dto.PlanDto;
import com.pjt.triptravel.plan.dto.PlanItemCreateParam;
import com.pjt.triptravel.plan.dto.PlanSimpleDto;
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

    public PlanDto getDetail(Long planId) {
        Plan plan = planRepository.findByIdWithDetail(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행 계획 번호입니다."));
        return PlanDto.of(plan);
    }

    @Transactional
    public Long createPlan(Long memberId, PlanCreateParam param) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);
        List<Long> attractionIds = param.getPlanItems()
                .stream().map(PlanItemCreateParam::getAttractionId)
                .collect(Collectors.toList());

        Map<Long, AttractionInfo> attractionIdMap = attractionRepository.findAllById(attractionIds)
                .stream()
                .collect(Collectors.toMap(
                        AttractionInfo::getId,
                        attraction -> attraction));

        Plan plan = Plan.builder()
                .title(param.getTitle())
                .description(param.getDescription())
                .startDateTime(param.getStartDateTime())
                .endDateTime(param.getEndDateTime())
                .member(member)
                .build();
        Plan savedPlan = planRepository.save(plan);

        List<PlanItem> planItems = new ArrayList<>();
        param.getPlanItems().forEach(itemParam -> planItems.add(PlanItem.builder()
                .attractionInfo(attractionIdMap.get(itemParam.getAttractionId()))
                .startDateTime(itemParam.getStartDateTime())
                .endDateTime(itemParam.getEndDateTime())
                .memo(itemParam.getMemo())
                .plan(plan)
                .build()));
        planItemRepository.saveAll(planItems);
        return savedPlan.getId();
    }
}
