package com.twozo.smapp.service;

import java.util.Collection;
import com.twozo.smapp.dao.UserDao;
import com.twozo.smapp.model.dto.UserDto;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(final UserDao userDao) {
		
		this.userDao = userDao;
	}


	public int addUser(final User userData) {

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
		}else if (phoneExists) {
			return 2;
		}

		final boolean userAdded = userDao.add(userData);

		if (userAdded) {
			return 3;
		}

		return 4;
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

	public boolean update(final UserDto userDto,int updateType){

		if(updateType == 1){
			final Collection<UserDto> allUsers = userDao.getAllUser();

			for (UserDto user : allUsers) {
				if (user.getName().equals(userDto.getName())) {
					return false;
				}
			}
			return  userDao.update(userDto,updateType);
		}else if(updateType == 2){
			final Collection<UserDto> allUsers = userDao.getAllUser();

			for (UserDto user : allUsers) {
				if (user.getPhNo().equals(userDto.getPhNo())) {
					return false;
				}
			}
			return userDao.update(userDto,updateType);
		}

		return userDao.update(userDto,updateType);
	}
}
