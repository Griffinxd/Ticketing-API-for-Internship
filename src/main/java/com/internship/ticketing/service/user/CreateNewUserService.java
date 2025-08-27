package com.internship.ticketing.service.user;

import com.internship.ticketing.Command;
import com.internship.ticketing.model.CustomUser;
import com.internship.ticketing.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateNewUserService implements Command<CustomUser, Void> {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private static final Logger logger = LogManager.getLogger();

    public CreateNewUserService(UserRepository userRepository,
                                PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<Void> execute(CustomUser customUser){
        logger.info("Executing {} with user details: {}", getClass(), customUser);
        Optional<CustomUser> optionalUser = userRepository.findById(customUser.getUsername());
        if (optionalUser.isEmpty()){
            userRepository.save(
                    new CustomUser(customUser.getUsername(), encoder.encode(customUser.getPassword()))
            );
            return ResponseEntity.ok().build();
        }
        throw new RuntimeException("User Already Exists"); // TODO: replace this exception with a proper one
    }
}
