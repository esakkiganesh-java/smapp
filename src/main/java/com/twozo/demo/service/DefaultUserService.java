package com.twozo.demo.service;

import java.util.Collection;

import com.twozo.demo.dao.UserDao;
import com.twozo.demo.model.Dto.UserDto;
import com.twozo.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

	private final UserDao userDao;
	
	public DefaultUserService(UserDao userDao) {
		
		this.userDao = userDao;
	}


	public int checkUserRegistration(User userData) {

		Collection<UserDto> allUserData = userDao.getAllUser();

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
		} else if (phoneExists) {
			return 2;
		}

		boolean userAdded = userDao.addUser(userData);
		if (userAdded) {
			return 3;
		}

		return 4;

	}

	public boolean addUser(User user) {

		return userDao.addUser(user);
	}

	public boolean deleteUser(UserDto userData) {

		return userDao.deleteUser(userData);
	
	}

	public int getUserId(String userData) {

		return userDao.getUserId(userData);
	}

	public UserDto getUser(String userData) {
		
		return userDao.getUser(userData);
	}

	public boolean checkValidUser(String userName) {

		Collection<UserDto> userData = userDao.getAllUser();
		for (UserDto user : userData) {
			if (user.getName().equals(userName)) {
				return false;
			}
		}
		return true;
	}

	public boolean updatePassword(UserDto userData) {

		return userDao.updateUserPassword(userData);
	}

	public boolean updateUserName(UserDto userData) {

		Collection<UserDto> allUsers = userDao.getAllUser();
		for (UserDto user : allUsers) {
			if (user.getName().equals(userData.getName())) {
				return false;
			}
		}
		return  userDao.updateUserName(userData);
	}

	public boolean updateUserPhNo(UserDto userData) {

		Collection<UserDto> allUsers = userDao.getAllUser();
		for (UserDto user : allUsers) {
			if (user.getPhNo().equals(userData.getPhNo())) {
				return false;
			}
		}
		return userDao.updatePhNo(userData);
	}
}
