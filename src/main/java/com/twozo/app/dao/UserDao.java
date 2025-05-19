package com.twozo.app.dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao<User,UserDto> extends GeneralDao<User,UserDto> {

	@Override
	boolean add(final User user);

	@Override
	boolean delete(final UserDto userDto);

	@Override
	boolean update(final UserDto userDto,final int updateType);

	UserDto getUser(final String userName);

	Collection<UserDto> getAllUser();

	int getUserId(final String userInfo);

}
