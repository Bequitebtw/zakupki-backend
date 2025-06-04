package ru.pro.zakupki.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 20, message = "Имя должно содержать от 2 до 20 букв")
    @Column(name = "name")
    private String username;
    @NotNull
    @NotBlank
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    @Column(name = "password", nullable = false)
    private String password;
    @NotNull
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$", message = "Неверно введен email")
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")  // Столбец с ролью
    private Role role;
    @Column(name = "created_at")
    private LocalDateTime created = LocalDateTime.now();
    @Column(name = "locked")
    private boolean locked;
    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
