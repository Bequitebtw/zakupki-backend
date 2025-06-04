package ru.pro.zakupki.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pro.zakupki.exception.ExistEmailException;
import ru.pro.zakupki.exception.NotFoundUserException;
import ru.pro.zakupki.mapper.UserMapper;
import ru.pro.zakupki.model.Role;
import ru.pro.zakupki.model.User;
import ru.pro.zakupki.model.dto.UserAdminDto;
import ru.pro.zakupki.model.requests.UserUpdateRequest;
import ru.pro.zakupki.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;
    private final SessionRegistry sessionRegistry;


    @Transactional
    public User findUserById(Long userId) {
        return userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким именем не найден"));
    }

    public void updateUserBlockStatus(Long userId, Boolean isLocked) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        user.setLocked(isLocked);
        userRepository.save(user);
        String username = user.getUsername();
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            if (principal instanceof UserDetails userDetails) {
                if (userDetails.getUsername().equals(username)) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation sessionInfo : sessions) {
                        sessionInfo.expireNow();
                    }
                }
            }
        }
    }

    @Transactional
    public void updateUserRole(Long userId, String role) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new NotFoundUserException("пользователь не найден"));
        user.setRole(Role.valueOf(role));
        userRepository.save(user);
    }

    @Transactional
    public User updateUserProfile(User user, UserUpdateRequest userUpdateRequest) {
        User bdUser = findUserByEmail(user.getEmail());
        bdUser.setEmail(userUpdateRequest.getEmail());
        bdUser.setUsername(userUpdateRequest.getUsername());
        UserDetails updatedUserDetails = customUserDetailService.loadUserByUsername(user.getEmail());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                updatedUserDetails.getPassword(),
                updatedUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return userRepository.save(bdUser);

    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ExistEmailException(user.getEmail());
        }
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createAdmin(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ExistEmailException(user.getEmail());
        }
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public List<UserAdminDto> findAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::mapToUserAdminDto).collect(Collectors.toList());
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundUserException(email));
    }

}