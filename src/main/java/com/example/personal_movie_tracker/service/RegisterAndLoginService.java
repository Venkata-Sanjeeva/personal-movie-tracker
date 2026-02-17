package com.example.personal_movie_tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.personal_movie_tracker.enums.Roles;
import com.example.personal_movie_tracker.exceptions.EmailAlreadyExistsException;
import com.example.personal_movie_tracker.exceptions.InvalidLoginCredentialsException;
import com.example.personal_movie_tracker.exceptions.UserNotFoundException;
import com.example.personal_movie_tracker.model.User;
import com.example.personal_movie_tracker.repository.UserRepository;
import com.example.personal_movie_tracker.utils.IdentifierGenerator;

@Service
public class RegisterAndLoginService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public RegisterAndLoginService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with " + email + " not found!"));
    }

    public User registerUser(String name, String email, String password, String role) {

        if (userRepo.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email + " already exists in DB");
        }

        User user = new User();
        
        String roleUC = role.toUpperCase();

        user.setUserUID(IdentifierGenerator.generate(roleUC));
        user.setName(name);
        user.setEmail(email);

        // ✅ Hash password using Argon2
        user.setPassword(passwordEncoder.encode(password));

        user.setRole(Roles.USER);

        return userRepo.save(user);
    }

    public boolean verifyUser(String userEmail, String userPassword) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new InvalidLoginCredentialsException("Invalid email or password"));
        return passwordEncoder.matches(userPassword, user.getPassword());
    }

}
