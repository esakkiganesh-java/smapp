package com.twozo.app.service;

import java.util.Collection;
import com.twozo.app.dao.UserDao;
import com.twozo.app.model.Dto.UserDto;
import com.twozo.app.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(final UserDao userDao) {
		
		this.userDao = userDao;
	}


	public int checkUserRegistration(final User userData) {

		final Collection<UserDto> allUserData = userDao.getAllUser();
		boolean nameExists = false;
		boolean phoneExists = false;

		for (UserDto user : allUserData) {

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
		}
		else if (phoneExists) {
			return 2;
		}

		final boolean userAdded = userDao.addUser(userData);

		if (userAdded) {
			return 3;
		}

		return 4;
	}

	public boolean addUser(final User user) {

		return userDao.addUser(user);
	}

	public boolean deleteUser(final UserDto userData) {

		return userDao.deleteUser(userData);
	
	}

	public int getUserId(final String userData) {

		return userDao.getUserId(userData);
	}

	public UserDto getUser(final String userData) {
		
		return userDao.getUser(userData);
	}

	public boolean checkValidUser(final String userName) {

		final Collection<UserDto> userData = userDao.getAllUser();

		for (UserDto user : userData) {
			if (user.getName().equals(userName)) {
				return false;
			}
		}

		return true;
	}

	public boolean updatePassword(final UserDto userData) {

		return userDao.updateUserPassword(userData);
	}

	public boolean updateUserName(final UserDto userData) {

		final Collection<UserDto> allUsers = userDao.getAllUser();

		for (UserDto user : allUsers) {
			if (user.getName().equals(userData.getName())) {
				return false;
			}
		}

		return  userDao.updateUserName(userData);
	}

	public boolean updateUserPhNo(final UserDto userData) {

		final Collection<UserDto> allUsers = userDao.getAllUser();

		for (UserDto user : allUsers) {
			if (user.getPhNo().equals(userData.getPhNo())) {
				return false;
			}
		}

		return userDao.updatePhNo(userData);
	}
}
