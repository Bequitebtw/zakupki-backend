package ru.pro.zakupki.model.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NonNull
    private List<OrderItemRequest> items;
    @NotNull
    private Long totalPrice;
}