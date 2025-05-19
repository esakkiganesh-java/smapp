package com.twozo.app.service;

import com.twozo.app.model.dto.UserDto;
import com.twozo.app.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	boolean addUser(final User user);

	int getUserId(final String phNo);

	UserDto getUser(final String phNo);

	boolean updateUserName(final UserDto userDto,int updateType);

	boolean updateUserPhNo(final UserDto userDto,int updateType);

	boolean updatePassword(final UserDto userDto,int updateType);

	boolean deleteUser(final UserDto userDto);

	int checkUserRegistration(final User user);

}
