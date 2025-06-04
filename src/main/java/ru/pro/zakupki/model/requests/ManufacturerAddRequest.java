package ru.pro.zakupki.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManufacturerAddRequest {
    private String name;
    private String description;
    private String country;
}
