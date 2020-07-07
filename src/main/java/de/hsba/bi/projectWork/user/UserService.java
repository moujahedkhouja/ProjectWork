package de.hsba.bi.projectWork.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import de.hsba.bi.projectWork.web.exception.IncorrectPasswordException;
import de.hsba.bi.projectWork.web.exception.UserAlreadyExistException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService { //implements IUserService

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /*public void init() {
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
    }*/

    public void save(User user) {
        userRepository.save(user);
    }


    // @Transactional
    // @Override
    public User createUser(RegisterUserForm userForm) throws UserAlreadyExistException {
        if (usernameExists(userForm.getName())) {
            throw new UserAlreadyExistException("There is an account with the username: " + userForm.getName());
        }
        User user = new User();
        user.setName(userForm.getName());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setRole("DEVELOPER");
        this.save(user);
        return user;
    }

    public boolean usernameExists(String name) {
        return userRepository.findByName(name).isPresent();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.orElse(null);
    }

    public List<User> findUsers() {
        return userRepository.findByRole(User.DEVELOPER_ROLE);
    }

    public User findCurrentUser() {
        Optional<User> user = userRepository.findByName(User.getCurrentUsername());
        return user.orElse(null);
    }

    public boolean checkOldPassword(String rawPassword, User user) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public ChangePasswordForm changePassword(ChangePasswordForm changePasswordForm) throws IncorrectPasswordException {
        // TODO Als Nutzer kann ich mein Passwort ändern

        // Load the authenticated user
        Optional<User> userOptional = userRepository.findByName(User.getCurrentUsername());

        if (userOptional.isPresent()) {
            // check if user was found
            User user = userOptional.get();

            // Check old password
            if (!checkOldPassword(changePasswordForm.getPassword(), user)) {
                throw new IncorrectPasswordException("The old password you entered is incorrect!");
            }

            // actually change password
            if(changePasswordForm.getPassword().equals(changePasswordForm.getMatchingPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordForm.getPassword()));
            this.save(user);
            }
        }

        return changePasswordForm;
    }

    public boolean changeRole(Long id, String role) {
        // TODO Als Admin kann ich die Rollen anderer Nutzer ändern
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setRole(role);
            userRepository.save(user);
        }
        return false;
    }

}
