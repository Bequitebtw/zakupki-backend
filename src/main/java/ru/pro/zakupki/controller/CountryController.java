package ru.pro.zakupki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.zakupki.model.Country;
import ru.pro.zakupki.service.CountryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping()
    public List<Country> findAllCountries() {
        return countryService.findAllCountries();
    }
}
