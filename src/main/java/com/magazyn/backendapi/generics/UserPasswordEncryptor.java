package com.magazyn.backendapi.generics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.magazyn.backendapi.entity.BaseUser;

@Component
public class UserPasswordEncryptor {

    private final PasswordEncoder passwordEncoder;


    public UserPasswordEncryptor(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public <T extends BaseUser> T encryptPasswordInBaseUser(T user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
