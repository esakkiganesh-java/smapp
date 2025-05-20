package com.twozo.smapp.validation;

import com.twozo.smapp.model.dto.MessageDto;
import com.twozo.smapp.model.dto.UserDto;
import com.twozo.smapp.model.Message;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class InputValidator implements Validator {

    @Override
    public Collection<String> validateRegister(final User user){

        Collection<String> invalidData = new ArrayList<>();

        if(user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
            invalidData.add("Invalid! User name should  not empty or not greater than 20 characters");
        }

        if (!user.getPhNo().matches("^[6-9][0-9]{9}$")) {
            invalidData.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
        }

        if(user.getPassword().length() < 8){
            invalidData.add("Invalid! Password must contains minimum 8 digits");
        }

        return invalidData;
    }

    @Override
    public Collection<String> validateUserDelete(final UserDto userDto){

        Collection<String> invalidData = new ArrayList<>();

        if(userDto.getName().trim().isEmpty() || userDto.getName().trim().length() > 20){
            invalidData.add("Invalid! User name should  not empty or not greater than 20 characters");
        }

        if (!userDto.getPhNo().matches("^[6-9][0-9]{9}$")) {
            invalidData.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
        }

        if(userDto.getPassword().length() < 8){
            invalidData.add("Invalid! Password must contains minimum 8 digits");
        }
        return invalidData;
    }

    @Override
    public Collection<String> validateUserDetailUpdates(final UserDto userDto){

        Collection<String> invalidData = new ArrayList<>();

        if(userDto.getName().trim().isEmpty() || userDto.getName().trim().length() > 20){
            invalidData.add("Invalid! User name should  not empty or not greater than 20 characters");
        }

        if(!userDto.getPhNo().matches("^[1-9][0-9]{9}$")) {
            invalidData.add("Invalid! Phone number must contains 10 positive digits and starts with(6 to 9)");
        }
        return invalidData;
    }

    @Override
    public Collection<String> validatePasswordUpdate(UserDto userDto) {
        Collection<String> invalidData = new ArrayList<>();
        try {
            if (userDto.getId() <= 0) {
                invalidData.add("Invalid! id must be greater than zero");
            }
        } catch(Exception e){
            invalidData.add("Invalid! id must be integer and greater than zero");
        }

        if(userDto.getPassword().length() < 8){
            invalidData.add("Invalid! Password must contains minimum 8 digits");
        }

        return invalidData;
    }

    @Override
    public Collection<String> validateSendMessage(final Message message){
        Collection<String> invalidData = new ArrayList<>();

        try {
            if (message.getSenderId() <= 0) {
                invalidData.add("invalid! sender Id must be greater than zero");
            }
        } catch(Exception e){
            invalidData.add("invalid! sender Id must be an integer and  greater than zero");
        }

        try {
            if (message.getReceiverId() <= 0) {
                invalidData.add("invalid! receiver Id must be greater than zero");
            }
        } catch(Exception e){
            invalidData.add("invalid! receiver Id must be an integer and greater than zero");
        }

        if (message.getContent().trim().isEmpty()){
            invalidData.add("invalid! Message content should not be empty");
        }

        return invalidData;
    }

    @Override
    public Collection<String> validateEditMessage(final MessageDto messageDto){
        Collection<String> invalidData = new ArrayList<>();

        try {
            if (messageDto.getId() <= 0) {
                invalidData.add("invalid! Message id must be greater than zero");
            }
        } catch(Exception e){
            invalidData.add("invalid! Message id must be an integer and greater than zero");
        }

        if(messageDto.getContent().trim().isEmpty()){
            invalidData.add("Message content should not be empty");
        }

        return invalidData;
    }

    @Override
    public boolean validateDeleteMessage(final int messageId){

        try {
            if (messageId <= 0) {
                return false;
            }
        } catch(Exception e){
            return false;
        }

        return true;
    }

    @Override
    public boolean validateGetInboxMessages(final int id){

        try {
            if (id <= 0) {
                return false;
            }
        } catch(Exception e){
            return false;
        }

        return true;
    }

    @Override
    public Collection<String> validateGetChatHistory(final MessageDto messageDto){
        Collection<String> invalidData = new ArrayList<>();

        try {
            if (messageDto.getSenderId() <= 0) {
                invalidData.add("invalid! sender id must be greater than zero");
            }
        } catch(Exception e){
            invalidData.add("nvalid! sender id must be an integer and greater than zero");
        }

        try {
            if (messageDto.getReceiverId() <= 0) {
                invalidData.add("invalid! receiver id must be greater than zero");
            }
        } catch(Exception e){
            invalidData.add("invalid! receiver id must be an integer and greater than zero");
        }

        return invalidData;
    }

    @Override
    public boolean validateUserDetail(final String phNo) {

        return (phNo.matches("^[1-9][0-9]{9}$"));

    }

}
