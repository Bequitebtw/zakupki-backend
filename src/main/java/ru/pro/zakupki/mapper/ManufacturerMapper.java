package ru.pro.zakupki.mapper;

import ru.pro.zakupki.model.Manufacturer;
import ru.pro.zakupki.model.dto.ManufacturerDto;

public class ManufacturerMapper {
    public static ManufacturerDto manufacturerToDto(Manufacturer manufacturer) {
        return ManufacturerDto.builder().id(manufacturer.getId()).name(manufacturer.getName()).build();
    }

}
