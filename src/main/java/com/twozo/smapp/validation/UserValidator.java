package com.twozo.smapp.validation;

import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
public  class UserValidator implements Validator<User> {

    private static final String phoneNumberFormat = "^[6-9][0-9]{9}$";

    @Override
    public Collection<String> validate(User user,ValidationType validationType) {

        final Collection<String> errors = new ArrayList<>();

        switch(validationType){
            case ADD -> {

                if(user.getName() == null || user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
                    errors.add("Invalid! User name should  not empty or not greater than 20 characters");
                }

                if(user.getPhone() == null || !user.getPhone().matches(phoneNumberFormat)){
                    errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
                }

                if(user.getPassword().length() < 8){
                    errors.add("Invalid! Password must contains minimum 8 digits");
                }

            }
            case UPDATE ->{

                if(user.getName() == null || user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
                    errors.add("Invalid! User name should  not empty or not greater than 20 characters");
                }

                if(user.getPhone() == null || !user.getPhone().matches(phoneNumberFormat)) {
                    errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
                }

            }
            case UPDATE_PASSWORD -> {

                if (user.getId() <= 0) {
                    errors.add("Invalid! id must be greater than zero");
                }

                if(user.getPassword().length() < 8){
                    errors.add("Invalid! Password must contains minimum 8 digits");
                }
            }
            case GET_USER -> {

                if (user.getPhone() == null || !user.getPhone().matches(phoneNumberFormat)) {
                    errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
                }

            }
            case CHECK_ID -> {

                if(user.getId() <= 0){
                    errors.add("Invalid!  id must be greater than zero");
                }

            }
            default -> {
                return errors;
            }
        }

    return errors;
    }
}