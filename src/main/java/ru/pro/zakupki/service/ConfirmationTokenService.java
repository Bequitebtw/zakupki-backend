package ru.pro.zakupki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.zakupki.model.ConfirmationToken;
import ru.pro.zakupki.model.User;
import ru.pro.zakupki.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    public ConfirmationToken createToken(User user) {
        ConfirmationToken token = new ConfirmationToken(user);
        return repository.save(token);
    }

    public ConfirmationToken validateToken(String tokenValue) {
        ConfirmationToken token = repository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Токен не найден"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Токен истёк");

        if (token.isConfirmed())
            throw new RuntimeException("Токен уже подтверждён");

        token.setConfirmed(true);
        repository.save(token);

        return token;
    }
}
