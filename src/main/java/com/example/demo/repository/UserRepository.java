package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    User findByUsername(String username);

    User save(User user);
}
