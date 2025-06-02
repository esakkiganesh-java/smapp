package com.twozo.smapp.service;

import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	String add(final User user);

	String update(final User user,String updateType);

	String delete(final User user);

	int getUserId(final String phone);

	User getUser(final String phone);

	String addToFavourites(final int userId,final int otherUserId);

	String removeFromFavourites(final int userId,final int otherUserId);

}
