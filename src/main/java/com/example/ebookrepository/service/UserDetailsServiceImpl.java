package com.example.ebookrepository.service;

import com.example.ebookrepository.model.User;
import com.example.ebookrepository.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null){
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), Collections.emptyList());
        }

        throw new UsernameNotFoundException(username);
    }
}
