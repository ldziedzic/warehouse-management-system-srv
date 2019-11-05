package com.dziedzic.warehouse.web.rest;

import com.dziedzic.warehouse.model.User;
import com.dziedzic.warehouse.security.TokenProvider;
import com.dziedzic.warehouse.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/warehouse/users")
public class UserResource {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    public UserResource(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/get_current")
    public User getCurrentUser(HttpServletRequest request) {
        String token = tokenProvider.getJwtFromRequest(request);
        return userService.getUserByJwtToken(token);
    }
}
