package com.bugtracker.back.controller;

import com.bugtracker.back.Services.UserService;
import com.bugtracker.back.dto.UserDTO;
import com.bugtracker.back.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<User> createUser(User user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<User> updateUser(User user) {
        try {
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<User> updateUserDetails(UserDTO userDTO) {
        try {
            User updatedUser = userService.updateUserDetails(userDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<User> findUserById(Long id) {
        try {
            User foundUser = userService.findUserById(id);
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        try {
            List<UserDTO> foundUsers = userService.findUsers();
            return new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Long id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
