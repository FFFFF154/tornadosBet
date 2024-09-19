package ru.box.tornadosbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.box.tornadosbet.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
