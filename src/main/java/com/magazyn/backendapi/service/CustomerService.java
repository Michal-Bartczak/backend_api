package com.magazyn.backendapi.service;

import com.magazyn.backendapi.dto.UserRegistrationDTO;
import com.magazyn.backendapi.entity.Customer;
import com.magazyn.backendapi.generics.UserPasswordEncryptor;
import com.magazyn.backendapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserPasswordEncryptor userPasswordEncryptor;

    public CustomerService(CustomerRepository customerRepository, UserPasswordEncryptor userPasswordEncryptor) {
        this.customerRepository = customerRepository;
        this.userPasswordEncryptor = userPasswordEncryptor;
    }

    public Customer compereToCustomer(UserRegistrationDTO registration) {
        Customer customer = new Customer();
        customer.setUsername(registration.getUsername());
        customer.setPassword(registration.getPassword());
        customer.setEmail(registration.getEmail());

        Customer encryptedCustomer = userPasswordEncryptor.encryptPasswordInBaseUser(customer);
        if (encryptedCustomer == null){

            throw new IllegalArgumentException("Pusty obiekt podczas rejestracji");
        }
        return encryptedCustomer;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
