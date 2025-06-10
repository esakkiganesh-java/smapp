package com.twozo.smapp.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypter {

    private static final int cost = 12;

    public String hashPassword(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(cost));
    }

    public boolean verifyPassword(final String password, final String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}

