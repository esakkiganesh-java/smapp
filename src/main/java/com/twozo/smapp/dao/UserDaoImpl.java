package com.twozo.smapp.dao;

import com.twozo.smapp.model.User;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


@Repository
public class UserDaoImpl implements UserDao {
	private final DataSource dataSource;

	public UserDaoImpl(final DataSource dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public boolean add(final User user) {

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

	@Override
	public boolean delete(final User user) {

		final String query = "UPDATE \"smapp_user\" SET is_deleted_user = ? WHERE ph_no = ? AND name = ? AND password = ?";
	    
	    try (Connection connection = dataSource.getConnection()) {
	        PreparedStatement stmt = connection.prepareStatement(query);

	        stmt.setBoolean(1, true);
	        stmt.setString(2, user.getPhNo());
	        stmt.setString(3, user.getName());
	        stmt.setString(4, user.getPassword());

			final  int userUpdated = stmt.executeUpdate();

	        return userUpdated > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean update(final User user,int updateType) {

		if(updateType == 1) {

			final String query = "UPDATE \"smapp_user\" SET name = ? WHERE ph_No = ?";

			try (Connection connection = dataSource.getConnection()) {
				PreparedStatement stmt = connection.prepareStatement(query);

				stmt.setString(1, user.getName());
				stmt.setString(2, user.getPhNo());

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

		else if(updateType == 2){

			final String query = "UPDATE \"smapp_user\" SET ph_no = ? WHERE name = ?";

			try (Connection connection = dataSource.getConnection()) {
				PreparedStatement stmt = connection.prepareStatement(query);

				stmt.setString(1, user.getPhNo());
				stmt.setString(2, user.getName());

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

		else if(updateType == 3){

			final String query = "UPDATE \"smapp_user\" SET password = ? WHERE id = ? ";

			try (Connection connection = dataSource.getConnection()) {
				PreparedStatement stmt = connection.prepareStatement(query);

				stmt.setString(1, user.getPassword());
				stmt.setInt(2, user.getId());

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

		return false;
	}

	public User getUser(final String phoneNo) {

		final String query = "SELECT id,name,ph_no FROM \"smapp_user\" WHERE  ph_no = ?";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, phoneNo);

			try (ResultSet resultSet = stmt.executeQuery()) {
				if (resultSet.next()) {
					final int id = resultSet.getInt("id");
					final String phNo = resultSet.getString("ph_no");
					final String name = resultSet.getString("name");
					return new User(id, phNo, name);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getUserId(final String phNo) {

		final String query = "SELECT id FROM \"smapp_user\" WHERE  ph_no = ?";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, phNo);

			try (ResultSet resultSet = stmt.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Collection<User> getAllUser() {

		final String query = "SELECT id,name,ph_no,password FROM \"smapp_user\"";
		final Collection<User> users = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(query);
			 ResultSet resultSet = stmt.executeQuery()) {

			while (resultSet.next()) {
				final int id = resultSet.getInt("id");
				final String phNo = resultSet.getString("ph_no");
				final String name = resultSet.getString("name");
				final String password = resultSet.getString("password");
				final User user = new User(id, phNo, name, password);

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

}
