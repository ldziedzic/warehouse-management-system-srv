package com.dziedzic.warehouse.service;


import com.dziedzic.warehouse.exception.OAuth2AuthenticationProcessingException;
import com.dziedzic.warehouse.exception.ResourceNotFoundException;
import com.dziedzic.warehouse.model.AuthProvider;
import com.dziedzic.warehouse.model.User;
import com.dziedzic.warehouse.repository.UserRepository;
import com.dziedzic.warehouse.security.TokenProvider;
import com.dziedzic.warehouse.security.UserPrincipal;
import com.dziedzic.warehouse.security.oauth2.user.OAuth2UserInfo;
import com.dziedzic.warehouse.security.oauth2.user.OAuth2UserInfoFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;


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

    public UserDetails loadUserByClientId(String clientId) {
        User user = userRepository.findByProviderId(clientId).orElseThrow(
                () -> new ResourceNotFoundException("User", "clientId", clientId)
        );
        return UserPrincipal.create(user);
    }

    public OAuth2User processOAuth2AndroidUser(GoogleIdToken.Payload payload) {
        String userId = payload.getSubject();
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        user = userOptional.map(value -> updateExistingUser(value, name, pictureUrl)).orElseGet(() -> registerNewUser(email, name, pictureUrl, AuthProvider.google, userId));

        return UserPrincipal.create(user, null);
    }

    private User registerNewUser(String email, String name, String pictureUrl, AuthProvider provider, String clientID) {
        User user = new User();

        user.setProvider(provider);
        user.setProviderId(clientID);
        user.setName(name);
        user.setEmail(email);
        user.setImageUrl(pictureUrl);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, String name, String imageUrl) {
        existingUser.setName(name);
        existingUser.setImageUrl(imageUrl);
        return userRepository.save(existingUser);
    }
}
