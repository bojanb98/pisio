package net.etfbl.pisio.authservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.authservice.model.entity.User;
import net.etfbl.pisio.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElse(null);
    }

    private boolean isPasswordValid(String newPassword) {
        return newPassword != null && newPassword.length() >= MIN_PASSWORD_LENGTH;
    }

    @Transactional
    public boolean isCredentialsValid(String username, String password) {
        User user = findByUsername(username);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }

    @Transactional
    public boolean addNewUser(String username, String password) {
        User user = findByUsername(username);

        if (user != null) {
            return false;
        }

        if (!isPasswordValid(password)) {
            return false;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        return true;
    }
}
