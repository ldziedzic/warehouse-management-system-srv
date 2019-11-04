package com.dziedzic.warehouse.service;


import com.dziedzic.warehouse.exception.ResourceNotFoundException;
import com.dziedzic.warehouse.model.User;
import com.dziedzic.warehouse.repository.UserRepository;
import com.dziedzic.warehouse.security.TokenProvider;
import com.dziedzic.warehouse.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
        );
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );
        return UserPrincipal.create(user);
    }

    public User getUserByJwtToken(String token) {
        Long userId = tokenProvider.getUserIdFromToken(token);

        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "jwt", token)
        );
    }
}
