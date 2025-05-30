package com.twozo.smapp.validation;

import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface Validator<T> {

    Collection<String> validate(final T t,ValidationType validationType);

}
