package com.twozo.smapp.service;

import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	boolean add(final User user);

	boolean update(final User user,String updateType);

	boolean delete(final User user);

	int getUserId(final String phone);

	User getUser(final String phone);

	boolean addToFavourites(final int userId,final int otherUserId);

	boolean removeFromFavourites(final int userId,final int otherUserId);

}
