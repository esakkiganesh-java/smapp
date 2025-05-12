package com.twozo.demo.dao;

import java.util.Collection;


import com.twozo.demo.model.Dto.UserDto;
import com.twozo.demo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

	boolean addUser(User user);

	boolean deleteUser(UserDto userDto);

	UserDto getUser(String userName);

	Collection<UserDto> getAllUser();

	int getUserId(String userInfo);

	boolean updateUserName(UserDto userDto);

	boolean updatePhNo(UserDto userDto);

	boolean updateUserPassword(UserDto userDto);

}
