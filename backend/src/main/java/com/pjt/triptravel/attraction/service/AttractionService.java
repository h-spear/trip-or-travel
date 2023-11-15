package com.pjt.triptravel.attraction.service;

import com.pjt.triptravel.attraction.dto.AttractionDto;
import com.pjt.triptravel.attraction.dto.AttractionSimpleDto;
import com.pjt.triptravel.attraction.entity.AttractionInfo;
import com.pjt.triptravel.attraction.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public List<AttractionSimpleDto> search() {
        return null;
    }

    public AttractionDto findOne(Long id) {
        AttractionInfo attractionInfo = attractionRepository.findByIdWithDescription(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관광지 번호입니다."));
        return AttractionDto.of(attractionInfo);
    }
}
