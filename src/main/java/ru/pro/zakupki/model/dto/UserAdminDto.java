package ru.pro.zakupki.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.pro.zakupki.model.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserAdminDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime created;
    private boolean locked;
    private boolean enabled;

}
