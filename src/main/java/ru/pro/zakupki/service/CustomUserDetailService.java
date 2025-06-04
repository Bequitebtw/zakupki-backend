package ru.pro.zakupki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pro.zakupki.exception.NotConfirmAccountException;
import ru.pro.zakupki.exception.NotFoundUserException;
import ru.pro.zakupki.model.User;
import ru.pro.zakupki.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Login attempt with username: " + username);
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new NotFoundUserException(username));
        if (!user.isEnabled()) {
            throw new NotConfirmAccountException();
        }
        if (user.isLocked()) {
            throw new RuntimeException();
        }
        return user;
    }
}
