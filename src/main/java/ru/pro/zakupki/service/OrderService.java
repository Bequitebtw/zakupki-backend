package ru.pro.zakupki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pro.zakupki.exception.NotFoundUserException;
import ru.pro.zakupki.model.*;
import ru.pro.zakupki.model.requests.OrderItemRequest;
import ru.pro.zakupki.model.requests.OrderRequest;
import ru.pro.zakupki.repository.OrderRepository;
import ru.pro.zakupki.repository.ProductRepository;
import ru.pro.zakupki.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createNewOrder(User user, OrderRequest orderRequest) {

        User dbUser = userRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundUserException(user.getEmail()));

        Order order = new Order();
        order.setUser(dbUser);
        order.setStatus(OrderStatus.ACTIVE);
        order.setTotalPrice(orderRequest.getTotalPrice());

        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));


            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPricePerItem(itemRequest.getPricePerItem());

            items.add(orderItem);

            productRepository.save(product);
        }

        order.setItems(items);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getUserActiveOrders(User user) {
        return orderRepository.findActiveAndReadyOrdersByUser(user.getId());
    }

    @Transactional(readOnly = true)
    public List<Order> getUserCompetedOrders(User user) {
        return orderRepository.findCompletedOrdersByUser(user.getId());
    }

    @Transactional
    public void markAllActiveOrdersAsCompleted() {
        List<Order> activeOrders = orderRepository.findByStatus(OrderStatus.ACTIVE);
        for (Order order : activeOrders) {
            order.setStatus(OrderStatus.READY);
        }
        orderRepository.saveAll(activeOrders);
    }

    @Transactional(readOnly = true)
    public List<Order> getUserOrdersById(Long userId) {
        return orderRepository.findOrdersByUser(userId);
    }

    @Transactional
    public void changeOrderStatusById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId).orElseThrow(RuntimeException::new);
        order.setStatus(OrderStatus.FINISHED);
        orderRepository.save(order);
    }
}
