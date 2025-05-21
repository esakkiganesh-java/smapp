package com.twozo.smapp.validation;

import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MessageValidator implements Validator<Message>{
    @Override
    public Collection<String> validate(Message message,int validationType) {

        final Collection<String> errors = new ArrayList<>();

        if(validationType == 1){

            try {
                if (message.getSenderId() <= 0) {
                    errors.add("invalid! sender Id must be greater than zero");
                }
            } catch(Exception e){
                errors.add("invalid! sender Id must be an integer and  greater than zero");
            }

            try {
                if (message.getReceiverId() <= 0) {
                    errors.add("invalid! receiver Id must be greater than zero");
                }
            } catch(Exception e){
                errors.add("invalid! receiver Id must be an integer and greater than zero");
            }

            if (message.getContent().trim().isEmpty()){
                errors.add("invalid! Message content should not be empty");
            }

        }else if(validationType == 2){

            try {
                if (message.getId() <= 0) {
                    errors.add("invalid! Message id must be greater than zero");
                }
            } catch(Exception e){
                errors.add("invalid! Message id must be an integer and greater than zero");
            }

            if(message.getContent().trim().isEmpty()){
                errors.add("Message content should not be empty");
            }

        }else if(validationType == 3){

            try {
                if (message.getId() <= 0) {
                    errors.add("invalid! id must be greater than zero");
                }
            } catch(Exception e){
                errors.add("Invalid! id must be an integer and greater than zero");
            }

        }else if(validationType == 4){

            try {
                if (message.getSenderId() <= 0) {
                    errors.add("invalid! sender id must be greater than zero");
                }
            } catch(Exception e){
                errors.add("invalid! sender id must be an integer and greater than zero");
            }

            try {
                if (message.getReceiverId() <= 0) {
                    errors.add("invalid! receiver id must be greater than zero");
                }
            } catch(Exception e){
                errors.add("invalid! receiver id must be an integer and greater than zero");
            }

        }else{
            errors.add("invalid validation type");
        }

    return errors;
    }
}
