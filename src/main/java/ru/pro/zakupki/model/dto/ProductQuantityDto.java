package ru.pro.zakupki.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pro.zakupki.model.Product;

@Data
@AllArgsConstructor
public class ProductQuantityDto {
    private Product product;
    private Long totalQuantity;
}