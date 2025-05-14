package com.twozo.app.service;

import com.twozo.app.model.Dto.UserDto;
import com.twozo.app.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	boolean addUser(final User user);

	int getUserId(final String userData);

	UserDto getUser(final String userData);

	boolean updateUserName(final UserDto userDto);

	boolean updateUserPhNo(final UserDto userDto);

	boolean updatePassword(final UserDto userDto);

	boolean deleteUser(final UserDto userDto);

	int checkUserRegistration(final User user);

}
