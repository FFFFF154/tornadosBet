package ru.box.tornadosbet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.repository.core.BoxerRepository;

import java.util.List;

@Service
public class BoxerService { //TODO прописать сервис

    @Autowired
    private BoxerRepository boxerRepository;

    public List<Boxer> allBoxers(){
        return boxerRepository.findAll();
    }
}
