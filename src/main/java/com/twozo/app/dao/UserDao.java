package com.twozo.app.dao;

import java.util.Collection;
import com.twozo.app.model.Dto.UserDto;
import com.twozo.app.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

	boolean addUser(final User user);

	boolean deleteUser(final UserDto userDto);

	UserDto getUser(final String userName);

	Collection<UserDto> getAllUser();

	int getUserId(final String userInfo);

	boolean updateUserName(final UserDto userDto);

	boolean updatePhNo(final UserDto userDto);

	boolean updateUserPassword(final UserDto userDto);

}
