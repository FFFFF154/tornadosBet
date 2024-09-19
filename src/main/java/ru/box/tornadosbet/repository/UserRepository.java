package ru.box.tornadosbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.box.tornadosbet.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}
