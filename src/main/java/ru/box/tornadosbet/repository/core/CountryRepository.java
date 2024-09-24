package ru.box.tornadosbet.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.box.tornadosbet.entity.postgresql.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    public Country findCountryByCountryName(String countryName);
}
