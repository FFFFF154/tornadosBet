package ru.box.tornadosbet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.box.tornadosbet.entity.postgresql.Country;
import ru.box.tornadosbet.repository.core.CountryRepository;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> allCountry(){
        return countryRepository.findAll();
    }
}
