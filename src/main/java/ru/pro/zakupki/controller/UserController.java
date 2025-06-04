package ru.pro.zakupki.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pro.zakupki.exception.NotFoundUserException;
import ru.pro.zakupki.model.ConfirmationToken;
import ru.pro.zakupki.model.User;
import ru.pro.zakupki.model.dto.UserProfileDto;
import ru.pro.zakupki.model.requests.UserUpdateRequest;
import ru.pro.zakupki.repository.UserRepository;
import ru.pro.zakupki.service.ConfirmationTokenService;
import ru.pro.zakupki.service.EmailService;
import ru.pro.zakupki.service.UserService;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ConfirmationTokenService tokenService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @GetMapping("/profile")
    public UserProfileDto getCurrentProfile(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new NotFoundUserException("Пользователь не зарегистрирован");
        }

        System.out.println(user.getUsername() + user.getEmail());
        User bdUser = userService.findUserByEmail(user.getEmail());

        return UserProfileDto.builder()
                .username(bdUser.getUsername())
                .email(bdUser.getEmail())
                .role(bdUser.getRole().getAuthority())
                .created(bdUser.getCreated())
                .build();
    }

    @PutMapping("/profile")
    public User updateProfile(@AuthenticationPrincipal User user, @RequestBody UserUpdateRequest userUpdateRequest) {
        if (!user.getEmail().equals(userUpdateRequest.getEmail())) {
            tokenService.createToken(user);
        }

        return userService.updateUserProfile(user, userUpdateRequest);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String tokenValue) {
        ConfirmationToken token = tokenService.validateToken(tokenValue);
        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok("Email подтвержден!");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        user.setEnabled(false);
        userService.createUser(user);

        ConfirmationToken token = tokenService.createToken(user);
        emailService.sendConfirmationEmail(user.getEmail(), token.getToken());

        return ResponseEntity.ok("Проверьте почту для подтверждения");
    }

}
