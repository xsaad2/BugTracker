package com.bugtracker.back.Services;

import com.bugtracker.back.dto.UserDTO;
import com.bugtracker.back.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

     User createUser(User user);

     User updateUser(User user);

     User updateUserDetails(UserDTO userDTO);

     List<UserDTO> findUsers();

     User findUserById(Long id);

     void deleteUserById(Long id);

     String signUpUser(User user);

     Optional<UserDetails> findByEmail(String email);

}
