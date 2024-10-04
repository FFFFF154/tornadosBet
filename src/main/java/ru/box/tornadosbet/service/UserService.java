package ru.box.tornadosbet.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.box.tornadosbet.dto.BoxerChoice;
import ru.box.tornadosbet.entity.Count;
import ru.box.tornadosbet.entity.mysql.Role;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.repository.security.RoleRepository;
import ru.box.tornadosbet.repository.security.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final static Long ADMIN_ID = 5L;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());

        if (userDB == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(roleRepository.findById(1L).orElse(null));
            user.setCount(new Count(0.0));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean authentication(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());

        if (!(userDB == null)) {
            if (userDB.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUserById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            return false;
        } else {
            userRepository.deleteById(id);
        }
        return true;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean updateUser(User user, Role checkRole) {
        User userDB = userRepository.findById(user.getId()).orElse(null);

        if (userDB != null) {
            checkRole.setRoleName("ROLE_" + checkRole.getRoleName());
            switch (checkRole.getRoleName().toUpperCase()) {
                case "ROLE_ADMIN" -> {
                    log.info("Admin activated");
                    userDB.setRoles(roleRepository.findById(2L).orElse(null));
                }
                case "ROLE_USER" -> {
                    log.info("User activated");
                    userDB.setRoles(roleRepository.findById(1L).orElse(null));
                }
                case "ROLE_" -> {
                    log.info("Role has been deleted!");
                    userDB.setRoles(null);
                }
            }
            if (!user.getPassword().equals(userDB.getPassword())) {
                if (!(user.getPassword().equals(""))) {
                    userDB.setPassword(passwordEncoder.encode(user.getPassword()));
                }

            }
            //user.setCount(new Count(userDB.getCount().getBalance()));
            userRepository.save(userDB);
            return true;
        }
        return false;
    }

    public boolean addBalance() {

        return false;
    }

    private boolean checkBalance(Long id, Double count) {
        Double balance = userRepository.findById(id).orElse(null).getCount().getBalance();
        return ((Double.compare(balance, count) == 1) || (Double.compare(balance, count) == 0));
    }

    public boolean transactionToAdmin(User user, Double count) {
        //User userDB = userRepository.findById(user.).orElse(null);
        User admin = userRepository.findById(ADMIN_ID).orElse(null);
        if (checkBalance(user.getId(), count)) {
            Double diffUser = user.getCount().getBalance() - count;
            user.setCount(new Count(diffUser));
            userRepository.save(user);
            
            Double diffAdmin = admin.getCount().getBalance() + count;
            admin.setCount(new Count(diffAdmin));
            userRepository.save(admin);
        } else {
            return false;
        }
        return true;
    }

    public boolean checkWin (Boxer boxerWin, BoxerChoice choice, User user, Double bid){
        User admin = userRepository.findById(ADMIN_ID).orElse(null);
        if(choice.getSelect().equals(boxerWin.getId().toString())){
            Double diff = user.getCount().getBalance() + bid;
            Double diffAdmin = admin.getCount().getBalance() - bid;
            admin.setCount(new Count(diffAdmin));
            userRepository.save(admin);

            user.setCount(new Count(diff));
            userRepository.save(user);
            return  true;
        }
        return false;
    }

    public void donateToUser(User user, Count count){
        Double balance = user.getCount().getBalance();
        user.setCount(new Count(balance + count.getBalance()));
        userRepository.save(user);
    }
}
