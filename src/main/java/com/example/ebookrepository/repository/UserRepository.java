package com.example.ebookrepository.repository;

import com.example.ebookrepository.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserByUsername(String username);

}
