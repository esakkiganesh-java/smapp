package com.twozo.smapp.validation;

import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class MessageValidator implements Validator<Message>{
    @Override
    public Collection<String> validate(Message message,ValidationType validationType) {

        final Collection<String> errors = new ArrayList<>();

        switch(validationType) {
            case ADD -> {

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
            case UPDATE -> {

                if (message.getId() <= 0) {
                    errors.add("invalid! Message id must be greater than zero");
                }

                if (message.getContent().trim().isEmpty()) {
                    errors.add("Message content should not be empty");
                }

            }
            case CHECK_ID -> {

                if (message.getId() <= 0) {
                    errors.add("invalid! id must be greater than zero");
                }

            }
            case CHAT_HISTORY -> {

                if (message.getSenderId() <= 0) {
                    errors.add("invalid! sender id must be greater than zero");
                }

                if (message.getReceiverId() <= 0) {
                    errors.add("invalid! receiver id must be greater than zero");
                }

            }
            default -> {
                return errors;
            }

        }

    return errors;
    }
}
