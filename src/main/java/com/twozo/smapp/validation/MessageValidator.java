package com.twozo.smapp.validation;

import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class MessageValidator implements Validator<Message>{

    @Override
    public Collection<String> validate(final Message message,final ValidationType validationType){

        final Collection<String> errors = new ArrayList<>();

        switch (validationType){
            case ADD -> validateAdd(message,errors);
            case UPDATE -> validateUpdate(message,errors);
            case CHECK_ID -> validateId(message,errors);
            case GET_CHAT -> validateGetChat(message,errors);
            default -> errors.add("invalid validation type");
        }

        return errors;
    }

    private void validateAdd(final Message message,final Collection<String> errors){

        if (message.getSenderId() <= 0) {
            errors.add("invalid! sender Id must be greater than zero");
        }

        if (message.getReceiverId() <= 0) {
            errors.add("invalid! receiver Id must be greater than zero");
        }

        if (message.getContent().trim().isEmpty()) {
            errors.add("invalid! Message content should not be empty");
        }
    }

    private void validateUpdate(final Message message,final Collection<String> errors){

        if (message.getId() <= 0) {
            errors.add("invalid! Message id must be greater than zero");
        }

        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            errors.add("Message content should not be empty");
        }
    }

    private void validateId(final Message message, final Collection<String> errors){

        if (message.getId() <= 0) {
            errors.add("invalid! id must be greater than zero");
        }
    }

    private void validateGetChat(final Message message, final Collection<String> errors){

        if (message.getSenderId() <= 0) {
            errors.add("invalid! sender id must be greater than zero");
        }

        if (message.getReceiverId() <= 0) {
            errors.add("invalid! receiver id must be greater than zero");
        }
    }
}
