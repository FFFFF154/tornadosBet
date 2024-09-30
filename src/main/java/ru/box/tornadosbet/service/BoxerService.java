package ru.box.tornadosbet.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.box.tornadosbet.dto.Bid;
import ru.box.tornadosbet.dto.BoxerChoice;
import ru.box.tornadosbet.dto.WinningOdds;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.entity.postgresql.Country;
import ru.box.tornadosbet.repository.core.BoxerRepository;
import ru.box.tornadosbet.repository.core.CountryRepository;

import java.util.List;

@Service
@Slf4j
public class BoxerService { //TODO прописать сервис

    @Autowired
    private BoxerRepository boxerRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<Boxer> allBoxers() {
        return boxerRepository.findAll();
    }

    public boolean addBox(Boxer boxer, Country country) {
        Boxer boxerDB = boxerRepository.findBoxerByPhoto(boxer.getPhoto());
        if (boxerDB != null) {
            return false;
        }
        Country countryDB = countryRepository.findCountryByCountryName(country.getCountryName());
        if (countryDB == null) {
            countryRepository.save(country);
            boxer.setCountry(country);
        } else {
            boxer.setCountry(countryDB);
        }
        boxerRepository.save(boxer);
        return true;
    }

    public Boxer winner(WinningOdds winningOdds) {
        Double sumChance = winningOdds.getChanceOne() + winningOdds.getChanceTwo();
        Double chanceOne = winningOdds.getChanceOne() * (1 / sumChance);
        Double chanceTwo = winningOdds.getChanceTwo() * (1 / sumChance);

        log.warn(chanceOne.toString());
        Boxer boxerWin = (Math.random()<chanceOne)?
                winningOdds.getFirstBoxer():winningOdds.getSecondBoxer();
        log.warn(boxerWin.toString());
        return boxerWin;
    }

    public Double prize(Boxer boxerWin, WinningOdds winningOdds, Bid bid){
        return (boxerWin.equals(winningOdds.getFirstBoxer()))?
                bid.getBid() * winningOdds.getChanceTwo():
                bid.getBid() * winningOdds.getChanceOne();
    }
}
