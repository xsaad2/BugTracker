package com.bugtracker.back.controller;

import com.bugtracker.back.dto.ResponseDTO;
import com.bugtracker.back.dto.UserDTO;
import com.bugtracker.back.security.SecurityService;
import com.bugtracker.back.utils.AppUserRoles;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@AllArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private SecurityService securityService;

    @Override
    public ResponseEntity<ResponseDTO> login(UserDTO userDTO) {
        return securityService.login(userDTO);
    }

    @Override
    public ResponseEntity<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        boolean loggedOut = securityService.logout(request);
        return ResponseEntity.status(200).body(loggedOut);
    }

    @Override
    public ResponseEntity<Boolean> isLoggedIn() {
        return ResponseEntity.ok().body(securityService.isLoggedIn());
    }

    @Override
    public ResponseEntity<Boolean> isAdmin() {
        boolean isAdmin = securityService.currentUserRoleIs(AppUserRoles.ADMIN.toString());
        return ResponseEntity.ok().body(isAdmin);
    }

    @Override
    public ResponseEntity<UserDTO> getCurrentUser(){
        return ResponseEntity.ok().body(securityService.getCurrentUserDto());
    }



}
