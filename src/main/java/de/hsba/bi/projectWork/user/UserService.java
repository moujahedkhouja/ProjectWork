package de.hsba.bi.projectWork.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void init() {
        createUser("timo", "timospassword", User.ADMIN_ROLE);
        createUser("ina", "inaspassword", User.ADMIN_ROLE);
        createUser("greta", "gretaspassword", User.ADMIN_ROLE);

        createUser("Anne", "123456", User.DEVELOPER_ROLE);
        createUser("Benedikt", "123456", User.DEVELOPER_ROLE);
        createUser("Charlotte", "123456", User.DEVELOPER_ROLE);
        createUser("Xenia", "123456", User.DEVELOPER_ROLE);
        createUser("Yves", "123456", User.DEVELOPER_ROLE);
        createUser("Zoe", "123456", User.DEVELOPER_ROLE);

        createUser("Karl", "123456", User.MANAGER_ROLE);
        createUser("Johanna", "123456", User.MANAGER_ROLE);
        createUser("Tina", "123456", User.MANAGER_ROLE);
    }

    public void createUser(String name, String password, String role) {
        userRepository.save(new User(name, passwordEncoder.encode(password), role));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findUsers() {
        return userRepository.findByRole(User.DEVELOPER_ROLE);
    }

    public User findCurrentUser() {
        Optional<User> user = userRepository.findByName(User.getCurrentUsername());
        return user.orElse(null);
    }

    public boolean changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) {
        // TODO Als Nutzer kann ich mein Passwort ändern
        Optional<User> user = userRepository.findByName(User.getCurrentUsername());
        return false;
    }


}
