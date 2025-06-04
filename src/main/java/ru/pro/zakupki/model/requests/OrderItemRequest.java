package ru.pro.zakupki.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    private Long pricePerItem;

}