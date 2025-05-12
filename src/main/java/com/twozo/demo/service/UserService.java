package com.twozo.demo.service;

import com.twozo.demo.model.Dto.UserDto;
import com.twozo.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	boolean addUser(User user);

	int getUserId(String userData);

	UserDto getUser(String userData);

	boolean updateUserName(UserDto userDto);

	boolean updateUserPhNo(UserDto userDto);

	boolean updatePassword(UserDto userDto);

	boolean deleteUser(UserDto userDto);

	int checkUserRegistration(User user);

}
