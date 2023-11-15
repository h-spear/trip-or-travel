package com.pjt.triptravel.attraction.service;

import com.pjt.triptravel.attraction.dto.attraction.*;
import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.attraction.repository.AttractionQueryRepository;
import com.pjt.triptravel.attraction.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionQueryRepository attractionQueryRepository;

    public Slice<AttractionSearchResult> search(AttractionSearchCondition condition, Pageable pageable) {
        return attractionQueryRepository.query(condition, pageable);
    }

    public List<AttractionAroundResult> findWithinRoundRange(AttractionAroundCondition condition) {
        return attractionRepository.findWithinRoundRange(condition.getLatitude(), condition.getLongitude(), condition.getRadiusKm());
    }

    public AttractionDto findOne(Long id) {
        AttractionInfo attractionInfo = attractionRepository.findByIdWithDescription(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관광지 번호입니다."));
        return AttractionDto.of(attractionInfo);
    }
}
