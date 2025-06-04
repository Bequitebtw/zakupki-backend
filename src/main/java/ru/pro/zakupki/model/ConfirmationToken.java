package ru.pro.zakupki.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean confirmed;

    public ConfirmationToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusHours(24);
        this.confirmed = false;
    }
}
