package com.twozo.smapp.service;

import com.twozo.smapp.model.dto.UserDto;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	int getUserId(final String phNo);

	UserDto getUser(final String phNo);

	boolean update(final UserDto userDto,int updateType);

	boolean deleteUser(final UserDto userDto);

	int addUser(final User user);

}
