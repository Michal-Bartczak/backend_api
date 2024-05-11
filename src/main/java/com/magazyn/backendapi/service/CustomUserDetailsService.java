package com.magazyn.backendapi.service;

import com.magazyn.backendapi.entity.Admin;
import com.magazyn.backendapi.entity.Customer;
import com.magazyn.backendapi.entity.Driver;
import com.magazyn.backendapi.entity.Employee;
import com.magazyn.backendapi.repository.AdminRepository;
import com.magazyn.backendapi.repository.CustomerRepository;
import com.magazyn.backendapi.repository.DriverRepository;
import com.magazyn.backendapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(AdminRepository adminRepository, CustomerRepository customerRepository,
                                    DriverRepository driverRepository, EmployeeRepository employeeRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (adminRepository.findByUsername(username).isPresent()) {
            Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new User(admin.getUsername(), admin.getPassword(), getAuthorities(admin.getRole()));
        } else if (customerRepository.findByUsername(username).isPresent()) {
            Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new User(customer.getUsername(), customer.getPassword(), getAuthorities(customer.getRole()));
        } else if (driverRepository.findByUsername(username).isPresent()) {
            Driver driver = driverRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new User(driver.getUsername(), driver.getPassword(), getAuthorities(driver.getRole()));
        } else if (employeeRepository.findByUsername(username).isPresent()) {
            Employee employee = employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new User(employee.getUsername(), employee.getPassword(), getAuthorities(employee.getRole()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
