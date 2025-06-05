package com.twozo.smapp.validation;

import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface Validator<Type> {

    Collection<String> validate(final Type type,ValidationType validationType);

}
