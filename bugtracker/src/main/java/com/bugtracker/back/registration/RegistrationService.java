package com.bugtracker.back.registration;

import com.bugtracker.back.Services.UserService;
import com.bugtracker.back.models.User;
import com.bugtracker.back.utils.AppUserRoles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;
    private UserService userService;

    public void register(RegistrationRequest request) {
        boolean isValid = emailValidator.test(request.email());
        if (!isValid)
            throw new IllegalStateException("E-Mail is not Valid!!");

        userService.signUpUser(
                new User(
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.password(),
                        AppUserRoles.USER));
    }
    public boolean emailAlreadyUsed(String email){
        return userService.findByEmail(email).isPresent();
    }
}
