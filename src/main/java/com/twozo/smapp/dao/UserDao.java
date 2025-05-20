package com.twozo.smapp.dao;

import java.util.Collection;

import com.twozo.smapp.model.User;
import com.twozo.smapp.model.dto.UserDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends GenericDao<User,UserDto> {

	boolean add(final User user);

	boolean delete(final UserDto userDto);

	boolean update(final UserDto userDto,final int updateType);

	UserDto getUser(final String userName);

	Collection<UserDto> getAllUser();

	int getUserId(final String userInfo);

}
