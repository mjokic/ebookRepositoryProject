package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Status;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/me")
    public ResponseEntity<?> getInfoAboutMe(Principal principal){
        if (principal == null){
            return new ResponseEntity<>(
                    new Status(false, "You're not logged in!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(
                user,
                HttpStatus.OK);

    }

}
