package ru.pro.zakupki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.zakupki.model.Image;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
