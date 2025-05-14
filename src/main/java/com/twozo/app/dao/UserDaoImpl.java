package com.twozo.app.dao;

import com.twozo.app.model.User;
import com.twozo.app.model.Dto.UserDto;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
	private final DataSource dataSource;

	public UserDaoImpl(final DataSource dataSource){
		this.dataSource = dataSource;
	}

	public boolean addUser(final User user) {

		final  String query = "INSERT INTO \"smapp_user\" (ph_no,name,password) VALUES (?,?,?)";
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setString(1, user.getPhNo());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getPassword());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteUser(final UserDto userDto) {
		final String query = "UPDATE \"smapp_user\" SET is_deleted_user = ? WHERE ph_no = ? AND name = ? AND password = ?";
	    
	    try (Connection connection = dataSource.getConnection()) {
	        PreparedStatement stmt = connection.prepareStatement(query);

	        stmt.setBoolean(1, true);
	        stmt.setString(2, userDto.getPhNo());
	        stmt.setString(3, userDto.getName());
	        stmt.setString(4, userDto.getPassword());

			final  int userUpdated = stmt.executeUpdate();

	        return userUpdated > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public UserDto getUser(final String userData) {

		final String query = "SELECT id,name,ph_no,password FROM \"smapp_user\" WHERE name = ? OR ph_no = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, userData);
			stmt.setString(2, userData);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("id");
					String phNo = rs.getString("ph_no");
					String name = rs.getString("name");
					return new UserDto(id, phNo, name);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getUserId(final String userData) {
		final String query = "SELECT id FROM \"smapp_user\" WHERE name = ? OR ph_no = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, userData);
			stmt.setString(2, userData);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Collection<UserDto> getAllUser() {
		final String query = "SELECT id,name,ph_no,password FROM \"smapp_user\"";
		List<UserDto> users = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String phNo = rs.getString("ph_no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				UserDto user = new UserDto(id, phNo, name, password);

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public boolean updateUserName(final UserDto userDto) {
		final String query = "UPDATE \"smapp_user\" SET name = ? WHERE ph_No = ?";
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setString(1, userDto.getName());
			stmt.setString(2, userDto.getPhNo());

			final int userNameUpdated = stmt.executeUpdate();

			if (userNameUpdated > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	public boolean updatePhNo(final UserDto userDto) {

		final String query = "UPDATE \"smapp_user\" SET ph_no = ? WHERE name = ?";
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setString(1, userDto.getPhNo());
			stmt.setString(2, userDto.getName());

			final int userNameUpdated = stmt.executeUpdate();

			if (userNameUpdated > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean updateUserPassword(final UserDto userDto) {
		final String query = "UPDATE \"smapp_user\" SET password = ? WHERE id = ? ";
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setString(1, userDto.getPassword());
			stmt.setInt(2, userDto.getId());

			final int passwordUpdated = stmt.executeUpdate();

			if (passwordUpdated > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
