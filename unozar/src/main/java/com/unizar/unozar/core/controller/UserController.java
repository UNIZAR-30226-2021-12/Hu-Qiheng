package com.unizar.unozar.core.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.entities.User;
import com.unizar.unozar.core.repository.UserRepository;

@RestController
public class UserController {
  @Autowired 
  UserRepository userRepository;
  
  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
      User newUser = userRepository.save(new User(user.getEmail(), 
          user.getAlias(), user.getPassword()));
      return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    } catch (Exception e) {
      // Cannot create user, most likely because email is already being used
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @DeleteMapping("/users/{user_code}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("user_code") UUID id) {
    try {
      userRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      // Cannot delete user, might be a duplicated request
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  

  @GetMapping("/users/email")
  public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
    try {
      // Retrieves the user whose account is linked to the email given
      User user = userRepository.findByEmail(email);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
