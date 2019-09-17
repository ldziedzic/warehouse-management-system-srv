package com.dziedzic.filecompresser.web.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExampleResource {

    @GetMapping("/index")
    public String index() {
        return "index data";
    }
}
