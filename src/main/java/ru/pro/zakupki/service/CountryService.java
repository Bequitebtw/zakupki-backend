package ru.pro.zakupki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.zakupki.model.Country;
import ru.pro.zakupki.repository.CountryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

}
