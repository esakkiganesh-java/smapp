package com.twozo.smapp.validation;

import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
public  class UserValidator implements Validator<User> {

    private static final String phoneNumberFormat = "^[6-9][0-9]{9}$";

    @Override
    public Collection<String> validate(final User user,final ValidationType validationType){
        final Collection<String> errors = new ArrayList<>();

        switch(validationType){
            case ADD -> validateAdd(user,errors);
            case UPDATE -> validateUpdate(user,errors);
            case UPDATE_PASSWORD -> validateUpdatePassword(user,errors);
            case GET_USER -> validateGetUser(user,errors);
            case CHECK_ID -> validateId(user,errors);
            default -> errors.add("Invalid validation type");
        }

        return errors;
    }

    private void validateAdd(final User user, final Collection<String> errors){

        if(user.getName() == null || user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
            errors.add("Invalid! User name should  not empty or not greater than 20 characters");
        }

        if(user.getPhone() == null || !user.getPhone().matches(phoneNumberFormat)){
            errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
        }

        if(user.getPassword() == null || user.getPassword().length() < 8){
            errors.add("Invalid! Password must contains minimum 8 digits");
        }
    }

    private void validateUpdate(final User user, final Collection<String> errors){

        if(user.getName() == null || user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
            errors.add("Invalid! User name should  not empty or not greater than 20 characters");
        }

        if(user.getPhone() == null || !user.getPhone().matches(phoneNumberFormat)) {
            errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
        }
    }

    private void validateId(final User user,final Collection<String> errors){

        if(user.getId() <= 0){
            errors.add("Invalid!  id must be greater than zero");
        }
    }

    private void validateGetUser(final User user, final Collection<String> errors){

        if (user.getPhone() == null || !user.getPhone().matches(phoneNumberFormat)) {
            errors.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
        }
    }

    private void validateUpdatePassword(final User user,final Collection<String> errors){

        if (user.getId() <= 0) {
            errors.add("Invalid! id must be greater than zero");
        }

        if(user.getPassword() == null || user.getPassword().length() < 8){
            errors.add("Invalid! Password must contains minimum 8 digits");
        }
    }
}