package ru.pro.zakupki.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ManufacturerDto {
    private Long id;
    private String name;
}
