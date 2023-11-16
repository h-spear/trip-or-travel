package com.pjt.triptravel.attraction.service;

import com.pjt.triptravel.attraction.dto.attraction.*;
import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.attraction.entity.ContentType;
import com.pjt.triptravel.attraction.repository.AttractionQueryRepository;
import com.pjt.triptravel.attraction.repository.AttractionRepository;
import com.pjt.triptravel.attraction.repository.ContentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionQueryRepository attractionQueryRepository;
    private final ContentTypeRepository contentTypeRepository;

    public List<ContentTypeDto> getContentTypes() {
        return contentTypeRepository.findAll()
                .stream().map(contentType -> new ContentTypeDto(contentType.getId(), contentType.getName()))
                .collect(Collectors.toList());
    }

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
