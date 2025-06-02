package com.twozo.smapp.service;

import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(final UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String add(final User user) {
		return userDao.add(user);
	}

	@Override
	public String delete(final User user) {
		return userDao.delete(user);
	}

	@Override
	public int getUserId(final String phone) {
		return userDao.getUserId(phone);
	}

	@Override
	public User getUser(final String phone) {
		return userDao.getUser(phone);
	}

	@Override
	public String update(final User user,final String updateType){
			return  userDao.update(user,updateType);
	}

	@Override
	public String addToFavourites(final int userId,final int otherUserId){
		return userDao.addToFavourites(userId, otherUserId);
	}

	@Override
	public String removeFromFavourites(final int userId,final int otherUserId){
		return userDao.removeFromFavourites(userId, otherUserId);
	}
}
