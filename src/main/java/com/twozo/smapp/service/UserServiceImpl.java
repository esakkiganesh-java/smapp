package com.twozo.smapp.service;

import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(final UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean add(final User user) {
		return userDao.add(user);
	}

	@Override
	public boolean delete(final User user) {
		return userDao.delete(user);
	}

	@Override
	public int getUserId(final String phone) {
		return userDao.getUserId(phone);
	}

	@Override
	public User getUser(final String phone) {
		return (User) userDao.getUser(phone);
	}

	@Override
	public boolean update(final User user,final String updateType){
			return  userDao.update(user,updateType);
	}

	@Override
	public boolean addToFavourites(final int userId,final int otherUserId){
		return userDao.addToFavourites(userId, otherUserId);
	}

	@Override
	public boolean removeFromFavourites(final int userId,final int otherUserId){
		return userDao.removeFromFavourites(userId, otherUserId);
	}
}
