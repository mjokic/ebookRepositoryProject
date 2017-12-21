package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.UserDto;
import com.example.ebookrepository.model.Status;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
                new UserDto(user),
                HttpStatus.OK);

    }

    @PostMapping("/me")
    public ResponseEntity<?> updateInfoAboutMe(Principal principal, @RequestBody UserDto userDto){
        if (principal == null){
            return new ResponseEntity<>(
                    new Status(false, "You're not logged in!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByUsername(principal.getName());

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userService.addEditUser(user);

        return new ResponseEntity<>(
                new Status(true, "Personal info successfully changed!"),
                HttpStatus.OK);
    }

    @PostMapping("/me/password")
    public ResponseEntity<?> updateMyPassword(Principal principal, @RequestBody UserDto userDto){
        if (principal == null){
            return new ResponseEntity<>(
                    new Status(false, "You're not logged in!"),
                    HttpStatus.BAD_REQUEST);
        }

        String password = userDto.getPassword();

        if (password == null){
            return new ResponseEntity<>(
                    new Status(false, "You didn't enter any password!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByUsername(principal.getName());
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userService.addEditUser(user);

        return new ResponseEntity<>(
                new Status(true, "Password successfully changed!"),
                HttpStatus.OK);
    }

}
