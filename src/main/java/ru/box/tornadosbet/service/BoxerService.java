package ru.box.tornadosbet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.box.tornadosbet.repository.core.BoxerRepository;

@Service
public class BoxerService { //TODO прописать сервис

    @Autowired
    private BoxerRepository boxerRepository;
}
