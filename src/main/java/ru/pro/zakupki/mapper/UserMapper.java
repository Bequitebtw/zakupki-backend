package ru.pro.zakupki.mapper;

import ru.pro.zakupki.model.User;
import ru.pro.zakupki.model.dto.UserAdminDto;
import ru.pro.zakupki.model.dto.UserProfileDto;


public class UserMapper {
    public static UserAdminDto mapToUserAdminDto(User user) {
        return UserAdminDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getAuthority())
                .created(user.getCreated())
                .locked(user.isLocked())
                .enabled(user.isEnabled())
                .build();
    }

    public static UserProfileDto mapToUserProfileDto(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getAuthority())
                .created(user.getCreated())
                .build();
    }
}
