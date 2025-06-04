package ru.pro.zakupki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.zakupki.model.Country;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {
    Optional<Country>findCountryByName(String name);
}
