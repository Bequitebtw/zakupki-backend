package ru.pro.zakupki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.pro.zakupki.mapper.UserMapper;
import ru.pro.zakupki.model.Order;
import ru.pro.zakupki.model.Role;
import ru.pro.zakupki.model.User;
import ru.pro.zakupki.model.dto.ManufacturerDto;
import ru.pro.zakupki.model.dto.ProductQuantityDto;
import ru.pro.zakupki.model.dto.UserAdminDto;
import ru.pro.zakupki.model.dto.UserProfileDto;
import ru.pro.zakupki.model.requests.ManufacturerAddRequest;
import ru.pro.zakupki.model.requests.ProductAddRequest;
import ru.pro.zakupki.model.requests.RoleUpdateRequest;
import ru.pro.zakupki.service.ManufacturerService;
import ru.pro.zakupki.service.OrderService;
import ru.pro.zakupki.service.ProductService;
import ru.pro.zakupki.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ManufacturerService manufacturerService;
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/manufacturer")
    public List<ManufacturerDto> findManufacturerNames() {
        return manufacturerService.getManufacturerNames();
    }

    @GetMapping("/users/{userId}")
    public UserProfileDto findUserById(@PathVariable Long userId) {
        return UserMapper.mapToUserProfileDto(userService.findUserById(userId));
    }

    @PostMapping("/manufacturer")
    public void createManufacturer(@RequestBody ManufacturerAddRequest manufacturer) {
        manufacturerService.createManufacturer(manufacturer);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long productId) {
        if (productService.deleteProductById(productId)) {
            return ResponseEntity.ok().body("Товар успешно удален");
        }
        return ResponseEntity.badRequest().body("Товар не получилось удалить");
    }

    @Transactional
    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@ModelAttribute ProductAddRequest product) throws IOException {
        productService.addProduct(product);
        return ResponseEntity.ok("Товар успешно добавлен");
    }

    @PutMapping("/{userId}/lock")
    public ResponseEntity<?> updateUserBlockStatus(@PathVariable Long userId, @RequestBody Boolean locked, @AuthenticationPrincipal User user) {
        User dbUser = userService.findUserById(userId);
        if (userId.equals(user.getId()) || dbUser.getRole().equals(Role.ROLE_ADMIN)) {
            return ResponseEntity.badRequest().body("Вы не можете заблокировать себя или администратора");
        }
        userService.updateUserBlockStatus(userId, locked);
        return ResponseEntity.ok().body("Статус блокировки изменен");
    }

    @GetMapping("/users/{userId}/orders")
    public List<Order> findUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrdersById(userId);
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<?> changeOrderStatusById(@PathVariable Long orderId) {
        orderService.changeOrderStatusById(orderId);
        return ResponseEntity.ok().body("Статус заказа успешно изменен");
    }

    @GetMapping("/users")
    public List<UserAdminDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long userId,
                                            @RequestBody RoleUpdateRequest request, @AuthenticationPrincipal User user) {

        String role = "ROLE_" + request.role();
        if (userId.equals(user.getId())) {
            return ResponseEntity.badRequest().body("Вы не можете поменять себе роль");
        }
        if (!role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.badRequest().body("Недопустимая роль: " + request.role());
        }

        userService.updateUserRole(userId, role);
        return ResponseEntity.ok().body("роль изменена");
    }

    @GetMapping("/order/{userId}")
    public List<Order> getUserOrderById(@PathVariable Long userId) {
        return null;
    }

    @GetMapping("/orders")
    public List<ProductQuantityDto> getUserOrders() {
        return productService.getAllActiveProducts();
    }

    @PutMapping("/orders-ready")
    public ResponseEntity<?> changeOrdersStatus() {
        try {
            orderService.markAllActiveOrdersAsCompleted();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка при обновлении заказов");
        }
    }
}
