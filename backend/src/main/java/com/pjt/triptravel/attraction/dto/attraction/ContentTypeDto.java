package com.pjt.triptravel.attraction.dto.attraction;

import lombok.Data;

@Data
public class ContentTypeDto {
    private Long id;
    private String name;

    public ContentTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
