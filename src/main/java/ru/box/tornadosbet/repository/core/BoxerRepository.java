package ru.box.tornadosbet.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.box.tornadosbet.entity.postgresql.Boxer;

@Repository
public interface BoxerRepository extends JpaRepository<Boxer, Long> {

    public Boxer findBoxerByPhoto(String photo);

}
