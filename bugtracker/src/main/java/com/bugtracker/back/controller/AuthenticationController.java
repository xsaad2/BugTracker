package com.bugtracker.back.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bugtracker.back.dto.ResponseDTO;
import com.bugtracker.back.dto.UserDTO;

/**
 * The AuthenticationController interface defines methods for handling authentication-related functionalities.
 */
@CrossOrigin
public interface AuthenticationController {

    /**
     * Handles logging in a user.
     *
     * @param user The user credentials to authenticate.
     * @return A ResponseEntity object containing a ResponseDTO object with the authentication result.
     */
    @PostMapping("/login")
    ResponseEntity<ResponseDTO> login(@RequestBody UserDTO user);

    /**
     * Checks if a user is logged in.
     *
     * @return A ResponseEntity object containing a Boolean value indicating if the user is logged in.
     */
    @GetMapping("/isloggedin")
    ResponseEntity<Boolean> isLoggedIn();

    /**
     * Checks if the current user is an admin.
     *
     * @return A ResponseEntity object containing a Boolean value indicating if the current user is an admin.
     */
    @GetMapping("/isAdmin")
    ResponseEntity<Boolean> isAdmin();

    /**
     * Gets the current user.
     *
     * @return A ResponseEntity object containing a UserDTO object representing the current user.
     */
    @GetMapping("/currentuser")
    ResponseEntity<UserDTO> getCurrentUser();

    /**
     * Handles logging out a user.
     *
     * @param request  The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @return A ResponseEntity object containing a Boolean value indicating if the user was logged out.
     */
    @DeleteMapping ("/login")
    ResponseEntity<Boolean> logout(HttpServletRequest request, HttpServletResponse response);
}