package com.twozo.smapp.validation;

import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
public  class UserValidator implements Validator<User> {
    @Override
    public Collection<String> validate(User user,int validationType) {

        final Collection<String> errors = new ArrayList<>();

        if(validationType == 1){

            if(user.getName() == null || user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
                errors.add("Invalid! User name should  not empty or not greater than 20 characters");
            }

            if (!user.getPhNo().matches("^[6-9][0-9]{9}$")) {
                errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
            }

            if(user.getPassword().length() < 8){
                errors.add("Invalid! Password must contains minimum 8 digits");
            }

        } else if(validationType == 2){

            if(user.getName() == null || user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
                errors.add("Invalid! User name should  not empty or not greater than 20 characters");
            }

            if(!user.getPhNo().matches("^[1-9][0-9]{9}$")) {
                errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
            }

        }else if(validationType == 3){

            try {
                if (user.getId() <= 0) {
                    errors.add("Invalid! id must be greater than zero");
                }
            } catch(Exception e){
                errors.add("Invalid! id must be integer and greater than zero");
            }

            if(user.getPassword().length() < 8){
                errors.add("Invalid! Password must contains minimum 8 digits");
            }

        }else if(validationType == 4){

            if (!user.getPhNo().matches("^[6-9][0-9]{9}$")) {
                errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
            }

        }else{
            errors.add("invalid validation type");
        }

    return errors;
    }
}
