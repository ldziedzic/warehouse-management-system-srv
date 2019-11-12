package com.dziedzic.warehouse.web.rest;

import com.dziedzic.warehouse.model.JWTToken;
import com.dziedzic.warehouse.security.TokenProvider;
import com.dziedzic.warehouse.service.UserService;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@RestController
@RequestMapping("/auth")
public class AuthResource {
    private TokenProvider tokenProvider;
    private UserService userService;

    public AuthResource(TokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @GetMapping("/browser_token_singin")
    public String token(@RequestParam String token) {
        return token;
    }

    @PostMapping("/android_token_signin")
    public ResponseEntity<JWTToken> androidTokenSignIn(@RequestParam String token, HttpServletRequest request) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),  new JacksonFactory())
                        .setAudience(Collections.singletonList("523539799843-hccnda578nhkun2kvmo279hd64u2q4ij.apps.googleusercontent.com"))
                        .setIssuer("https://accounts.google.com")
                        .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            userService.processOAuth2AndroidUser(payload);


            UserDetails userDetails = userService.loadUserByClientId(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("authorization", "Bearer " + jwtToken);
            return new ResponseEntity<>(new JWTToken(jwtToken), httpHeaders, HttpStatus.OK);
        } else {
            System.out.println("Invalid ID token.");
        }
        return null;
    }
}
