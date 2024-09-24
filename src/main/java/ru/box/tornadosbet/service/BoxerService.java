package ru.box.tornadosbet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.entity.postgresql.Country;
import ru.box.tornadosbet.repository.core.BoxerRepository;
import ru.box.tornadosbet.repository.core.CountryRepository;

import java.util.List;

@Service
public class BoxerService { //TODO прописать сервис

    @Autowired
    private BoxerRepository boxerRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<Boxer> allBoxers(){
        return boxerRepository.findAll();
    }

    public boolean addBox(Boxer boxer, Country country){
        Boxer boxerDB = boxerRepository.findBoxerByPhoto(boxer.getPhoto());
        if (boxerDB != null){
            return false;
        }
        Country countryDB = countryRepository.findCountryByCountryName(country.getCountryName());
        if (countryDB == null){
            countryRepository.save(country);
            boxer.setCountry(country);
        } else {
            boxer.setCountry(countryDB);
        }
        boxerRepository.save(boxer);

//        Boxer boxer = new Boxer();
//        boxer.setFirstName(boxerDTO.getFirstName());
//        boxer.setSecondName(boxerDTO.getSecondName());
//        boxer.setAge(boxerDTO.getAge());
//        boxer.setPhoto(boxerDTO.getPhoto());
//        boxer.setHeight(boxerDTO.getHeight());
//        boxer.setWeight(boxerDTO.getWeight());
//        boxer.setReach(boxerDTO.getReach());
//        boxer.setDivision(boxerDTO.getDivision());
//
        return true;
    }
}
