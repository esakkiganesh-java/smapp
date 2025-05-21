package com.twozo.smapp.service;

import java.util.Collection;
import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(final UserDao userDao) {
		
		this.userDao = userDao;
	}


	public int add(final User userData) {

		final Collection<User> allUserData = userDao.getAllUser();

		boolean nameExists = false;
		boolean phoneExists = false;

		for (User user : allUserData) {

			if (user.getPhNo().equals(userData.getPhNo())) {
				phoneExists = true;
				break;
			}

			if (user.getName().equals(userData.getName())) {
				nameExists = true;
				break;
			}

		}

		if (nameExists) {
			return 1;
		}else if (phoneExists) {
			return 2;
		}

		final boolean userAdded = userDao.add(userData);

		if (userAdded) {
			return 3;
		}

		return 4;
	}

	public boolean delete(final User user) {

		return userDao.delete(user);
	
	}

	public int getUserId(final String phNo) {

		return userDao.getUserId(phNo);
	}

	public User getUser(final String phNo) {
		
		return (User) userDao.getUser(phNo);
	}

	public boolean update(final User user,int updateType){

		if(updateType == 1){
			final Collection<User> allUsers = userDao.getAllUser();

			for (User allUser : allUsers) {
				if (user.getName().equals(allUser.getName())) {
					return false;
				}
			}
			return  userDao.update(user,updateType);
		}else if(updateType == 2){
			final Collection<User> allUsers = userDao.getAllUser();

			for (User allUser : allUsers) {
				if (user.getPhNo().equals(allUser.getPhNo())) {
					return false;
				}
			}
			return userDao.update(user,updateType);
		}

		return userDao.update(user,updateType);
	}
}
