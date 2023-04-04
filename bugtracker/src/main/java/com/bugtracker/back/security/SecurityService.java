package com.bugtracker.back.security;

import com.bugtracker.back.Services.UserService;
import com.bugtracker.back.dto.ResponseDTO;
import com.bugtracker.back.dto.UserDTO;
import com.bugtracker.back.models.User;
import com.bugtracker.back.session.SessionRegistry;
import com.bugtracker.back.utils.AppUserRoles;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityService {

    private UserService userService;

    private AuthenticationManager manager;

    private SessionRegistry sessionRegistry;
    private ModelMapper mapper;
    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
               ;
    }

    public User getAuthenticatedUserDetails() {
        Optional<Object> principal = getAuthentication().map(Authentication::getPrincipal);
        if(principal.isPresent() && principal.get() instanceof User){
            return (User) principal.get();
        }
        return null;
    }

    public UserDTO getCurrentUserDto(){
        User authenticatedUser = getAuthenticatedUserDetails();
        if(authenticatedUser == null) return null;
        User currentUser = userService.findUserById(authenticatedUser.getId());
        return this.mapper.map(currentUser,UserDTO.class);
    }

    public User getCurrentUser(){
        User authenticatedUser = getAuthenticatedUserDetails();
        if(authenticatedUser == null) return null;
        return userService.findUserById(authenticatedUser.getId());
    }

    public List<GrantedAuthority> getCurrentUserAuthorities(){
        return (List<GrantedAuthority>) getAuthenticatedUserDetails().getAuthorities();
    }

    public ResponseEntity<ResponseDTO> login(UserDTO userDTO) {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            final String sessionId = sessionRegistry.registerSession(userDTO.getUsername());
            ResponseDTO response = new ResponseDTO();
            response.setSessionId(sessionId);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            ResponseDTO response = new ResponseDTO();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    private void createDemoAccountsIfNotExist() {
        boolean adminExists = userService.findByEmail("admin@mail.us").isPresent();
        boolean userExists = userService.findByEmail("user@mail.us").isPresent();
        if(!adminExists){
            userService.signUpUser(
                    new User(
                            "Admin",
                            "\uD83D\uDE1D \uD83D\uDE0E \uD83D\uDC6E\uD83C\uDFFB",
                            "admin@mail.us",
                            "admin123",
                            AppUserRoles.ADMIN));
        }
        if(!userExists){
            userService.signUpUser(
                    new User(
                            "DemoUser",
                            "\uD83D\uDE1D \uD83D\uDE0E \uD83D\uDE0E ",
                            "user@mail.us",
                            "user123",
                            AppUserRoles.USER));
        }
    }

    public Boolean logout(HttpServletRequest request) {
        createDemoAccountsIfNotExist();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        SecurityContextHolder.clearContext();
        return true;
    }

    public Boolean currentUserRoleIs(String role){
       return getCurrentUser().getAuthorities()
                .contains(new SimpleGrantedAuthority(role));
    }

    public Boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof AnonymousAuthenticationToken)
                && (authentication.getPrincipal() instanceof UserDetails);

    }



}
