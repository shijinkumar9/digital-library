package com.example.digitallibrary.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/common/hello")
    public String commonHello() {
        return "Hello USER or ADMIN";
    }
}
