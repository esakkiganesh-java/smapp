package com.twozo.app.service;

import java.util.Collection;
import com.twozo.app.dao.UserDao;
import com.twozo.app.model.dto.UserDto;
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

		final boolean userAdded = userDao.add(userData);

		if (userAdded) {
			return 3;
		}

		return 4;
	}

	public boolean addUser(final User user) {

		return userDao.add(user);
	}

	public boolean deleteUser(final UserDto userDto) {

		return userDao.delete(userDto);
	
	}

	public int getUserId(final String phNo) {

		return userDao.getUserId(phNo);
	}

	public UserDto getUser(final String phNo) {
		
		return (UserDto) userDao.getUser(phNo);
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

	public boolean updatePassword(final UserDto userData,int updateType) {

		return userDao.update(userData,updateType);
	}

	public boolean updateUserName(final UserDto userDto,int updateType) {

		final Collection<UserDto> allUsers = userDao.getAllUser();

		for (UserDto user : allUsers) {
			if (user.getName().equals(userDto.getName())) {
				return false;
			}
		}

		return  userDao.update(userDto,updateType);
	}

	public boolean updateUserPhNo(final UserDto userDto,int updateType) {

		final Collection<UserDto> allUsers = userDao.getAllUser();

		for (UserDto user : allUsers) {
			if (user.getPhNo().equals(userDto.getPhNo())) {
				return false;
			}
		}

		return userDao.update(userDto,updateType);
	}
}
