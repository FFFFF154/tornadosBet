package ru.box.tornadosbet.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.entity.postgresql.Country;
import ru.box.tornadosbet.entity.postgresql.Division;

import java.util.List;

@Repository
public interface BoxerRepository extends JpaRepository<Boxer, Long> {

    public Boxer findBoxerByPhoto(String photo);

    public List<Boxer> findAllByDivision(Division division);

    public List<Boxer> findAllByCountry(Country country);

    public List<Boxer> findAllByDivisionAndCountry(Division division, Country country);

}
