package com.magazyn.backendapi.service;

import com.magazyn.backendapi.repository.AdminRepository;
import com.magazyn.backendapi.repository.CustomerRepository;
import com.magazyn.backendapi.repository.DriverRepository;
import com.magazyn.backendapi.repository.EmployeeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final DriverRepository driverRepository;
    private final EmployeeRepository employeeRepository;


    public SecurityService(CustomerRepository customerRepository, AdminRepository adminRepository,
                           DriverRepository driverRepository, EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
        this.driverRepository = driverRepository;
        this.employeeRepository = employeeRepository;
    }

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else if (authentication != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
    public Long getIdByUsername(String username) {
        List<Function<String, Long>> repositoryFunctions = Arrays.asList(
                customerRepository::findIdByUsername,
                adminRepository::findIdByUsername,
                driverRepository::findIdByUsername,
                employeeRepository::findIdByUsername
        );
        return repositoryFunctions.stream()
                .map(func -> func.apply(username))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
    public Collection<String> getUserRoles(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> role.substring(5))
                    .collect(Collectors.toList());
        }
        return null;
    }

}