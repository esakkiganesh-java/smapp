package com.twozo.smapp.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypter {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean verifyPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}

