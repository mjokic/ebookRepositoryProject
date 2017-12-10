package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Status;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final UserService userService;

    public MainController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(){
        return "Hey";
    }


    @RequestMapping(path = "/register", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Status> register(@RequestBody User user){
        user.setType("pretplatnik");
        userService.addEditUser(user);
        Status status = new Status("success", "Registration Successful!");
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
