package com.internship.ticketing.service.user;

import com.internship.ticketing.model.CustomUser;
import com.internship.ticketing.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger();

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.info("Executing {} username: {}", getClass(), username);
        Optional<CustomUser> optionalCustomUser = userRepository.findById(username);
        if (optionalCustomUser.isEmpty()){
            throw new RuntimeException("User not found"); // TODO: replace this exception
        }
        CustomUser user = optionalCustomUser.get();
        return User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
