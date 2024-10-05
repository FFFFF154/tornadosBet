package ru.box.tornadosbet.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.box.tornadosbet.entity.postgresql.Division;

public interface DivisionRepository extends JpaRepository<Division, Long> {

    public Division findByDivisionName(String divisionName);
}
