package ru.pro.zakupki.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserProfileDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime created;
}
