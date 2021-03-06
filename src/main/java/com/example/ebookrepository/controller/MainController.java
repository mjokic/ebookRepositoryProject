package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Status;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MainController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Status> register(@RequestBody User user) {
        user.setType("subscriber");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.addEditUser(user);
        Status status = new Status(true, "Registration Successful!");
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
