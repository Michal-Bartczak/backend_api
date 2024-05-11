package com.magazyn.backendapi.controller;

import com.magazyn.backendapi.dto.UserRegistrationDTO;
import com.magazyn.backendapi.service.CustomerService;
import com.magazyn.backendapi.service.DriverService;
import com.magazyn.backendapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/registration")
public class RegistrationController {
    private final String EMAIL_ALERT = "Podany email jest już zajęty";
    private final String USERNAME_ALERT = "Podana nazwa użytkownika jest już zajęta";
    private final String CONFIRM_SAVE_USER = "Rejestracja kierowcy przebiegła pomyślnie";

    private final UserService userService;
    private final UserRegistrationDTO userRegistrationDTO;
    private final CustomerService customerService;
    private final DriverService driverService;

    public RegistrationController(UserService userService, UserRegistrationDTO userRegistrationDTO, CustomerService customerService, DriverService driverService) {
        this.userService = userService;
        this.userRegistrationDTO = userRegistrationDTO;
        this.customerService = customerService;
        this.driverService = driverService;
    }

    @PostMapping("/driver")
    public ResponseEntity<?> registerDriver(@Validated @RequestBody UserRegistrationDTO userRegistrationDTO) {
        if (!userService.checkExistEmailForAllUsers(userRegistrationDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(EMAIL_ALERT);
        }
        if (!userService.checkExistUsernameForAllUsers(userRegistrationDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(USERNAME_ALERT);
        }
        driverService.saveDriver(driverService.compereToDriver(userRegistrationDTO));
        return ResponseEntity.ok(CONFIRM_SAVE_USER);
    }

    @PostMapping("/customer")
    public ResponseEntity<?> registerCustomer(@Validated @RequestBody UserRegistrationDTO userRegistrationDTO) {
        if (!userService.checkExistEmailForAllUsers(userRegistrationDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(EMAIL_ALERT);
        }
        if (!userService.checkExistUsernameForAllUsers(userRegistrationDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(USERNAME_ALERT);
        }
        customerService.saveCustomer(customerService.compereToCustomer(userRegistrationDTO));
        return ResponseEntity.ok(CONFIRM_SAVE_USER);
    }
}
