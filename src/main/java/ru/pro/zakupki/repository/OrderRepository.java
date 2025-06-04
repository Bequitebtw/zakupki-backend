package ru.pro.zakupki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.pro.zakupki.model.Order;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
                SELECT o FROM Order o 
                WHERE o.user.id = :userId 
                  AND (o.status = ru.pro.zakupki.model.OrderStatus.ACTIVE 
                       OR o.status = ru.pro.zakupki.model.OrderStatus.READY)
                ORDER BY o.createdAt DESC
            """)
    List<Order> findActiveAndReadyOrdersByUser(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = ru.pro.zakupki.model.OrderStatus.FINISHED ORDER BY o.createdAt DESC")
    List<Order> findCompletedOrdersByUser(@Param("userId") Long userId);

    List<Order> findByStatus(@Param("status") ru.pro.zakupki.model.OrderStatus status);

    @Query("""
                SELECT o FROM Order o 
                WHERE o.user.id = :userId 
                ORDER BY o.createdAt DESC
            """)
    List<Order>findOrdersByUser(@Param("userId") Long userId);

    Optional<Order>findOrderById(Long orderId);
}
