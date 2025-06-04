package ru.pro.zakupki.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pro.zakupki.model.Order;
import ru.pro.zakupki.model.User;
import ru.pro.zakupki.model.requests.OrderRequest;
import ru.pro.zakupki.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest,
                                         @AuthenticationPrincipal User user) {
        Order order = orderService.createNewOrder(user, orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/active")
    public List<Order> getUserActiveOrders(@AuthenticationPrincipal User user) {
        return orderService.getUserActiveOrders(user);
    }

    @GetMapping("/history")
    public List<Order> getUserHistoryOrders(@AuthenticationPrincipal User user) {
        return orderService.getUserCompetedOrders(user);
    }
}
