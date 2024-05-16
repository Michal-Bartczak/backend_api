package com.magazyn.backendapi.service;

import com.magazyn.backendapi.dto.UserRegistrationDTO;
import com.magazyn.backendapi.entity.Driver;
import com.magazyn.backendapi.generics.UserPasswordEncryptor;
import com.magazyn.backendapi.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserPasswordEncryptor userPasswordEncryptor;

    public DriverService(DriverRepository driverRepository, UserPasswordEncryptor encryptor) {
        this.driverRepository = driverRepository;
        this.userPasswordEncryptor = encryptor;
    }

    public Driver compereToDriver(UserRegistrationDTO registration) {

        Driver driver = new Driver();
        driver.setName(registration.getName());
        driver.setEmail(registration.getEmail());
        driver.setPassword(registration.getPassword());
        driver.setSurname(registration.getSurname());
        driver.setUsername(registration.getUsername());

        Driver encryptedDriver = userPasswordEncryptor.encryptPasswordInBaseUser(driver);
        if (encryptedDriver == null) {
         throw new IllegalArgumentException("Pusty obiekt podczas rejestracji");
        }
        return encryptedDriver;
    }

    public void saveDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers(){
        return driverRepository.findAll();
    }
}
