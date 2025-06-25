package com.twozo.smapp.service;

import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.User;
import com.twozo.smapp.utils.PasswordEncrypter;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final PasswordEncrypter passwordEncrypter;
	
	public UserServiceImpl(final UserDao userDao, final PasswordEncrypter passwordEncrypter) {
		this.userDao = userDao;
		this.passwordEncrypter = passwordEncrypter;
	}

	@Override
	public void add(final User user) {
		String hashedPassword = passwordEncrypter.hashPassword(user.getPassword());
		user.setPassword(hashedPassword);
	}

	@Override
	public void delete(final User user) {
		 userDao.delete(user);
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
	public void update(final User user, final String updateType) {
		if(("password").equals(updateType)) {
			String hashedPassword = passwordEncrypter.hashPassword(user.getPassword());
			user.setPassword(hashedPassword);
		}

		userDao.update(user,updateType);
	}

	@Override
	public boolean addToFavourites(final int userId, final int otherUserId){
		return userDao.addToFavourites(userId, otherUserId);
	}

	@Override
	public boolean removeFromFavourites(final int userId, final int otherUserId){
		return userDao.removeFromFavourites(userId, otherUserId);
	}
}
