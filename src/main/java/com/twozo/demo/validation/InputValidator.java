package com.twozo.demo.validation;

import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Dto.UserDto;
import com.twozo.demo.model.Message;
import com.twozo.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public class InputValidator implements Validator {

    @Override
    public boolean validateRegister(User user){
        if(user.getName().isEmpty()){
            return false;
        }
        else if (!user.getPhNo().matches("^[1-9][0-9]{9}$")) {
            return false;
        }
        else return user.getPassword().length() >= 8;
    }

    @Override
    public boolean validateUserDelete(UserDto userDto){
        if(userDto.getName().isEmpty()){
            return false;
        }
        else if (!userDto.getPhNo().matches("^[1-9][0-9]{9}$")) {
            return false;
        }
        else return userDto.getPassword().length() >= 8;
    }

    @Override
    public boolean validateUserDetailUpdates(UserDto userDto){
        if(userDto.getName().isEmpty()){
            return false;
        }
        return(userDto.getPhNo().matches("^[1-9][0-9]{9}$")) ;
    }

    @Override
    public boolean validatePasswordUpdate(UserDto userDto) {
        try {
            if (userDto.getId() <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }

        return userDto.getPassword().length() >= 8;
    }

    @Override
    public boolean validateSendMessage(Message message){
        try {
            if (message.getSenderId() <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
        try {
            if (message.getReceiverId() <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
        return (!message.getMessageContent().isEmpty());
    }

    @Override
    public boolean validateEditMessage(MessageDto messageDto){
        try {
            if (messageDto.getMessageId() <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
    return(!messageDto.getMessageContent().isEmpty());
    }

    @Override
    public boolean validateDeleteMessage(int messageId){
        try {
            if (messageId <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
    return true;
    }

    @Override
    public boolean validateGetInboxMessages(int id){
        try {
            if (id <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
    return true;
    }

    @Override
    public boolean validateGetChatHistory(MessageDto messageDto){
        try {
            if (messageDto.getSenderId() <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
        try {
            if (messageDto.getReceiverId() <= 0) {
                return false;
            }
        }catch(Exception e){
            return false;
        }
    return true;
    }

    @Override
    public boolean validateUserDetail(String userInfo){
        return !userInfo.isEmpty();
    }

}
