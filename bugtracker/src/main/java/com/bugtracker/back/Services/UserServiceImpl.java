package com.bugtracker.back.Services;

import com.bugtracker.back.dto.UserDTO;
import com.bugtracker.back.exceptions.NotFoundException;
import com.bugtracker.back.models.User;
import com.bugtracker.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final String USER_NOT_FOUND = "User with email %s not found";
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper mapper;

    public User createUser(User user) {
        return userRepo.save(user);
    }
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUserDetails(UserDTO userDTO) {
        User foundUser = findUserById(userDTO.getId());
        foundUser.setRole(userDTO.getRole());
        foundUser.setFirstName(userDTO.getFirstName());
        foundUser.setLastName(userDTO.getLastName());
        return userRepo.save(foundUser);
    }

    public List<UserDTO> findUsers() {
        return userRepo.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public User findUserById(Long id) {
        return userRepo.findEmployeeById(id)
                .orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));
    }

    public void deleteUserById(Long id) {
        userRepo.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String
                        .format(USER_NOT_FOUND, email)));
    }

    @Override
    public String signUpUser(User user) {
        boolean userExists = userRepo
                .findByEmail(user.getEmail())
                .isPresent();
        if (userExists)
            throw new IllegalStateException("email already exists!");
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);

        return "Signed UP";
    }
    @Override
    public Optional<UserDetails> findByEmail(String email){
        return userRepo.findByEmail(email);
    }

}
