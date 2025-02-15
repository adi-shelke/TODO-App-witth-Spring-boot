package com.adi.to_do_app.Service;

import com.adi.to_do_app.model.MyUser;
import com.adi.to_do_app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public MyUser register(MyUser user){

        MyUser existingEmailUser = userRepository.findByEmail(user.getEmail());
        MyUser existingUsernameUser = userRepository.findByUsername(user.getUsername());
        if (existingEmailUser != null) {
            throw new IllegalArgumentException("EMAIL_ALREADY_EXISTS");
        }

        // Check if username already exists
        if (existingUsernameUser!= null) {
            throw new IllegalArgumentException("USERNAME_ALREADY_EXISTS");
        }
        // Encode the password before saving it to the database.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public MyUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public MyUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
