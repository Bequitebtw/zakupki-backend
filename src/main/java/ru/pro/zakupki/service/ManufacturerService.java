package ru.pro.zakupki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.zakupki.mapper.ManufacturerMapper;
import ru.pro.zakupki.model.Country;
import ru.pro.zakupki.model.Manufacturer;
import ru.pro.zakupki.model.dto.ManufacturerDto;
import ru.pro.zakupki.model.requests.ManufacturerAddRequest;
import ru.pro.zakupki.repository.CountryRepository;
import ru.pro.zakupki.repository.ManufacturerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final CountryRepository countryRepository;

    public List<ManufacturerDto> getManufacturerNames() {
        return manufacturerRepository.findAll().stream().map(ManufacturerMapper::manufacturerToDto).collect(Collectors.toList());
    }

    public Manufacturer findManufacturerById(Long id) {
        return manufacturerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void createManufacturer(ManufacturerAddRequest newManufacturer) {
        Country country = countryRepository.findCountryByName(newManufacturer.getCountry()).orElseThrow(RuntimeException::new);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(newManufacturer.getName());
        manufacturer.setDescription(newManufacturer.getDescription());
        manufacturer.setCountry(country);
        manufacturerRepository.save(manufacturer);
    }
}
