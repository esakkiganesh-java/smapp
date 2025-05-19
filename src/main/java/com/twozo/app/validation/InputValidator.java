package com.twozo.app.validation;

import com.twozo.app.model.dto.MessageDto;
import com.twozo.app.model.dto.UserDto;
import com.twozo.app.model.Message;
import com.twozo.app.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InputValidator implements UserInputValidator {

    @Override
    public int validateRegister(final User user){

        if(user.getName().trim().isEmpty() || user.getName().trim().length() > 20){
            return 1;
        }

        else if (!user.getPhNo().matches("^[6-9][0-9]{9}$")) {
            return 2;
        }

        else if(user.getPassword().length() < 8){
            return 3;
        }

        return 4;
    }

    @Override
    public int validateUserDelete(final UserDto userDto){

        if(userDto.getName().trim().isEmpty() || userDto.getName().trim().length() > 20){
            return 1;
        }

        else if (!userDto.getPhNo().matches("^[1-9][0-9]{9}$")) {
            return 2;
        }

        else if(userDto.getPassword().length() < 8){
            return 3;
        }

        return 4;
    }

    @Override
    public int validateUserDetailUpdates(final UserDto userDto){

        if(userDto.getName().trim().isEmpty() || userDto.getName().trim().length() > 20){
            return 1;
        }

        else if(!userDto.getPhNo().matches("^[1-9][0-9]{9}$")) {
            return 2;
        }

        return 3;
    }

    @Override
    public int validatePasswordUpdate(UserDto userDto) {
        try {
            if (userDto.getId() <= 0) {
                return 1;
            }
        }
        catch(Exception e){
            return 1;
        }

        if(userDto.getPassword().length() < 8){
            return 2;
        }

        return 4;
    }

    @Override
    public int validateSendMessage(final Message message){
        try {
            if (message.getSenderId() <= 0) {
                return 1;
            }
        }
        catch(Exception e){
            return 1;
        }

        try {
            if (message.getReceiverId() <= 0) {
                return 2;
            }
        }
        catch(Exception e){
            return 2;
        }

        if (message.getMessageContent().trim().isEmpty()){
            return 3;
        }

        return 4;
    }

    @Override
    public int validateEditMessage(final MessageDto messageDto){
        try {
            if (messageDto.getId() <= 0) {
                return 1;
            }
        }
        catch(Exception e){
            return 1;
        }

        if(messageDto.getMessageContent().trim().isEmpty()){
            return 2;
        }

        return 3;
    }

    @Override
    public boolean validateDeleteMessage(final int messageId){
        try {
            if (messageId <= 0) {
                return false;
            }
        }
        catch(Exception e){
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
        }
        catch(Exception e){
            return false;
        }

        return true;
    }

    @Override
    public int validateGetChatHistory(final MessageDto messageDto){
        try {
            if (messageDto.getSenderId() <= 0) {
                return 1;
            }
        }
        catch(Exception e){
            return 1;
        }

        try {
            if (messageDto.getReceiverId() <= 0) {
                return 2;
            }
        }
        catch(Exception e){
            return 2;
        }

        return 3;
    }

    @Override
    public boolean validateUserDetail(final String phNo) {

        return (phNo.matches("^[1-9][0-9]{9}$"));

    }

}
