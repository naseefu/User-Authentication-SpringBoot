package com.hospitly.controller;

import com.hospitly.dto.Response;
import com.hospitly.entity.User;
import com.hospitly.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/{pass}")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid User user, @PathVariable String pass) {
        System.out.println(user.getPassword());
        Response r = userService.register(user, pass);
        return ResponseEntity.status(r.getStatusCode()).body(r);

    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<Response> loginUser(@PathVariable String email, @PathVariable String password) {

        Response r = userService.login(email, password);
        return ResponseEntity.status(r.getStatusCode()).body(r);

    }

}
