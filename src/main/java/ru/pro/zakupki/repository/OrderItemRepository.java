package ru.pro.zakupki.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pro.zakupki.model.OrderItem;
import ru.pro.zakupki.model.dto.ProductQuantityDto;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
                SELECT new ru.pro.zakupki.model.dto.ProductQuantityDto(oi.product, SUM(oi.quantity))
                FROM OrderItem oi
                WHERE oi.order.status = ru.pro.zakupki.model.OrderStatus.ACTIVE
                GROUP BY oi.product
            """)
    List<ProductQuantityDto> findProductQuantitiesInActiveOrders();
}
