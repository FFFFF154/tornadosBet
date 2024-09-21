package ru.box.tornadosbet.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.box.tornadosbet.entity.mysql.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
