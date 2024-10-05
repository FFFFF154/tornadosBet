package ru.box.tornadosbet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.box.tornadosbet.entity.postgresql.Division;
import ru.box.tornadosbet.repository.core.DivisionRepository;

import java.util.List;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;


    public List<Division> allDivision() {
        return divisionRepository.findAll();
    }

}
