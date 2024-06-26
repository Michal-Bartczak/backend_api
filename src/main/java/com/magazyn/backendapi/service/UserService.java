package com.magazyn.backendapi.service;

import com.magazyn.backendapi.dto.UserDetailsDTO;
import com.magazyn.backendapi.repository.AdminRepository;
import com.magazyn.backendapi.repository.CustomerRepository;
import com.magazyn.backendapi.repository.DriverRepository;
import com.magazyn.backendapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final DriverRepository driverRepository;
    private final AdminRepository adminRepository;
    private final SecurityService securityService;

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, DriverRepository driverRepository, AdminRepository adminRepository, SecurityService securityService) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.driverRepository = driverRepository;
        this.adminRepository = adminRepository;
        this.securityService = securityService;
    }

    public boolean checkExistEmailForAllUsers(String email) {
        return Stream.of(
                        adminRepository.findByEmail(email),
                        driverRepository.findByEmail(email),
                        customerRepository.findByEmail(email),
                        employeeRepository.findByEmail(email))
                .anyMatch(Optional::isPresent);
    }

    public boolean checkExistUsernameForAllUsers(String username) {
        return Stream.of(
                        adminRepository.findByUsername(username),
                        customerRepository.findByUsername(username),
                        driverRepository.findByUsername(username),
                        employeeRepository.findByUsername(username))
                .anyMatch(Optional::isPresent);
    }
    public UserDetailsDTO getCurrentUserUsernameAndRoles() {
        return new UserDetailsDTO(securityService.getAuthenticatedUsername(), securityService.getUserRoles());
    }


}
