package com.example.ebookrepository.service;

import com.example.ebookrepository.model.User;
import com.example.ebookrepository.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(int userId) {
        return userRepository.findOne(userId);
    }

    public void addEditUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(int userId) {
        userRepository.delete(userId);
    }
}
