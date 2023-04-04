package com.bugtracker.back.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *The RegistrationController class handles HTTP requests related to user registration.
 *
 * It receives requests and delegates the corresponding actions to the RegistrationService class.
 */
@RestController
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    /**
     *This method handles HTTP POST requests for user registration.
     * It receives a RegistrationRequest object as the request body and delegates
     * the registration process to the RegistrationService class. If the registration
     * is successful, it returns a HTTP 200 response. Otherwise, it returns a HTTP 409 response.
     *
     * @param request request a RegistrationRequest object representing the user registration details
     * @return a ResponseEntity object representing the HTTP response
     */
    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request){
        try{
            registrationService.register(request);
            return ResponseEntity.status(200).build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(409).build();
        }
    }

    /**
     * This method handles HTTP GET requests for checking if an email address is already registered.
     *
     * @param email email a String representing the email address to check
     * @return a ResponseEntity object with a boolean value representing if the email is already used and an HTTP response
     */
    @GetMapping("/emailcheck/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email){
        System.out.println("in email check");
        try{
            boolean res = registrationService.emailAlreadyUsed(email);
            return ResponseEntity.status(200).body(res);
        }catch (Exception e){
            return ResponseEntity.status(409).build();
        }
    }

}
