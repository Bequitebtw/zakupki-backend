package ru.pro.zakupki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.zakupki.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findUserById(Long id);
    Optional<User>findUserByUsername(String name);
    Optional<User>findUserByEmail(String email);

}
