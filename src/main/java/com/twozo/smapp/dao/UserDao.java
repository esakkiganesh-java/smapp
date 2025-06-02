package com.twozo.smapp.dao;

import java.util.Collection;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends Dao<User> {

	User getUser(final String phone);

	Collection<User> getAllUser();

	int getUserId(final String phone);

	String addToFavourites(final int userId,final int otherUserId);

	String removeFromFavourites(final int userId,final int otherUserId);

}
