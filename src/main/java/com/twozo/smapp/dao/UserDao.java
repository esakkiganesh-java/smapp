package com.twozo.smapp.dao;

import java.util.Collection;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends Dao<User> {

	boolean add(final User user);

	boolean delete(final User userDto);

	boolean update(final User user,final int updateType);

	User getUser(final String userName);

	Collection<User> getAllUser();

	int getUserId(final String userInfo);

}
