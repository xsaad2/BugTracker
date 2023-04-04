package com.bugtracker.back.controller;

import com.bugtracker.back.dto.UserDTO;
import com.bugtracker.back.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The UserController interface defines RESTful APIs for CRUD operations on User resource
 */
public interface UserController {

    /**
     * Create a new user resource
     *
     * @param user the User object to be created
     * @return a ResponseEntity with the created User object and HTTP status code
     */
    @PostMapping("/user")
    ResponseEntity<User> createUser(@RequestBody User user);

    /**
     * Update an existing user resource
     *
     * @param user  the User object to be updated
     * @return a ResponseEntity with the updated User object and  HTTP status code
     */
    @PutMapping("/user")
    ResponseEntity<User> updateUser(@RequestBody User user);

    /**
     * Update details of an existing user resource
     *
     * @param userDTO the UserDTO object containing the updated details of the user
     * @return a ResponseEntity with the updated User object and  HTTP status code
     */
    @PutMapping("/userdetails")
    ResponseEntity<User> updateUserDetails(@RequestBody UserDTO userDTO);

    /**
     * Find a user resource by its id
     *
     * @param id the id of the user resource to be found
     * @return ResponseEntity with the User object and HTTP status code
     */
    @GetMapping("/user/{id}")
    ResponseEntity<User> findUserById(@PathVariable Long id);

    /**
     * Find all users
     *
     * @return  ResponseEntity with a list of UserDTO objects and HTTP status code
     */
    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> findAllUsers();

    /**
     * Delete a user resource by its id
     *
     * @param id the id of the user resource to be deleted
     * @return  ResponseEntity<Void> HTTP status code with no response body
     */
    @DeleteMapping("/user/{id}")
    ResponseEntity<Void> deleteUserById(@PathVariable Long id);


}
