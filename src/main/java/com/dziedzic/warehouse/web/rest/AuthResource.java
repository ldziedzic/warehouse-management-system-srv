package com.dziedzic.warehouse.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthResource {

    @GetMapping("/token")
    public String token(@RequestParam String token) {
        return token;
    }
}