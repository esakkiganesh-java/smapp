package com.twozo.smapp.service;

import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	int getUserId(final String phNo);

	User getUser(final String phNo);

	boolean update(final User user,int updateType);

	boolean delete(final User user);

	int add(final User user);

}
