package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.UserDto;
import com.example.ebookrepository.model.Category;
import com.example.ebookrepository.model.Status;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.CategoryService;
import com.example.ebookrepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserController(UserService userService, CategoryService categoryService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping("/me")
    public ResponseEntity<?> getInfoAboutMe(Principal principal) {
        if (principal == null) {
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
    public ResponseEntity<?> updateInfoAboutMe(Principal principal, @RequestBody UserDto userDto) {
        if (principal == null) {
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
    public ResponseEntity<?> updateMyPassword(Principal principal, @RequestBody UserDto userDto) {
        if (principal == null) {
            return new ResponseEntity<>(
                    new Status(false, "You're not logged in!"),
                    HttpStatus.BAD_REQUEST);
        }

        String password = userDto.getPassword();

        if (password == null) {
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


    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream().map(UserDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        String type;
        switch (userDto.getType()){
            case "administrator":
                type = "administrator";
                break;
            default:
                type = "subscriber";
        }


        Category category = categoryService.getCategoryById(userDto.getCategoryId());

        User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getUsername(),
                bCryptPasswordEncoder.encode(userDto.getPassword()), type);
        user.setCategory(category);
        userService.addEditUser(user);

        return new ResponseEntity<>(
                new Status(true, "New user successfully created!"),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> editUser(@RequestBody User user) {

        return new ResponseEntity<>(
                new Status(true, "User successfully edited!"),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(
                new Status(true, "User successfully deleted!"),
                HttpStatus.OK);
    }

}
