package com.twozo.smapp.dao;

import com.twozo.smapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
class UserDaoImpl implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	private final DataSource dataSource;

	public UserDaoImpl(final DataSource dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public String add(final User user) {

		final  String query = "INSERT INTO \"smapp_user\" (phone,name,password) VALUES (?,?,?)";

		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement stmt = connection.prepareStatement(query)){

			stmt.setString(1, user.getPhone());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getPassword());

			if(stmt.executeUpdate() > 0){
				return("User registered successfully!");
			}

		} catch (SQLException exception) {
			logger.error("Failed to add user! name={} phone={} password={}", user.getName(), user.getPhone(), user.getPassword(), exception);
			return exception.toString();
		}

		return ("User not registered");
	}

	@Override
	public String delete(final User user) {

		final String query = "UPDATE \"smapp_user\" SET is_deleted_user = ? WHERE phone = ? AND name = ? AND password = ?";
	    
	    try (final Connection connection = dataSource.getConnection();
	         final PreparedStatement stmt = connection.prepareStatement(query)) {

	        stmt.setBoolean(1, true);
	        stmt.setString(2, user.getPhone());
	        stmt.setString(3, user.getName());
	        stmt.setString(4, user.getPassword());

			final  int userDeleted = stmt.executeUpdate();

	        if(userDeleted > 0){
				return("User deleted successfully");
			}

	    } catch (SQLException exception) {
	        logger.error("failed to delete user! name={} phone={} password={}", user.getName(), user.getPhone(), user.getPassword(), exception);
	        return exception.toString();
	    }

		return("User details not found/User not deleted");
	}

	@Override
	public String update(final User user, final String updateType){
		String query = null;
		String firstParameter = null;
		String secondParameter = null;
		int numberOfParam = 2;
		int id = -1;

		switch(updateType) {
			case ("name") -> {
				query = "UPDATE \"smapp_user\" SET name = ? WHERE phone = ?";
				firstParameter = user.getName();
				secondParameter = user.getPhone();
			}
			case ("phone") -> {
				query = "UPDATE \"smapp_user\" SET phone = ? WHERE name = ? ";
				firstParameter = user.getPhone();
				secondParameter = user.getName();
			}
			case ("password") -> {
				query = "UPDATE \"smapp_user\" SET password = ? WHERE id = ?";
				firstParameter = user.getPassword();
				id = user.getId();
				numberOfParam = 1;
			}
			default -> {
				return ("User details not updated!");
			}
		}

		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement stmt = connection.prepareStatement(query)){

			 if(numberOfParam == 1){
				 stmt.setString(1,firstParameter);
				 stmt.setInt(2,id);
			 } else{
				 stmt.setString(1,firstParameter);
				 stmt.setString(2,secondParameter);
			 }

			 final int updated = stmt.executeUpdate();

			 if(updated > 0){
				 return ("User details updated successfully!");
			 }

		}catch(Exception exception){
			logger.error("Failed to update user details", exception);
			return exception.toString();
		}

		return ("User details not found/User details not updated");
	}

	public User getUser(final String phoneNo) {

		final String query = "SELECT id,name,phone FROM \"smapp_user\" WHERE  phone = ?";

		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, phoneNo);
			final ResultSet resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					final int id = resultSet.getInt("id");
					final String phone = resultSet.getString("phone");
					final String name = resultSet.getString("name");
					return new User(id, phone, name);
				}

		} catch (SQLException exception) {
			logger.error("Failed to get User! phone={}", phoneNo, exception);
		}

		return null;
	}

	public int getUserId(final String phone) {

		final String query = "SELECT id FROM \"smapp_user\" WHERE  phone = ?";

		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement stmt = connection.prepareStatement(query)) {
			 stmt.setString(1, phone);

			try (final ResultSet resultSet = stmt.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("id");
				}
			}

		} catch (SQLException exception) {
			logger.error("Failed to get User id! phone={}", phone, exception);
		}

		return -1;
	}

	public Collection<User> getAllUser() {

		final String query = "SELECT id,name,phone,password FROM \"smapp_user\"";
		final Collection<User> users = new ArrayList<>();

		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement stmt = connection.prepareStatement(query);
			 final ResultSet resultSet = stmt.executeQuery()) {

			while (resultSet.next()) {
				final int id = resultSet.getInt("id");
				final String phone = resultSet.getString("phone");
				final String name = resultSet.getString("name");
				final String password = resultSet.getString("password");
				final User user = new User(id, phone, name, password);

				users.add(user);
			}

		} catch (SQLException exception) {
			logger.error("Failed to get all user",exception);
		}

		return users;
	}

	public String addToFavourites(final int userId,final int otherUserId){
		final String query = "INSERT INTO \"smapp_favourite_users\" (user_id,favourite_user_id) VALUES (?,?)";
		try(final Connection connection = dataSource.getConnection();
		    final PreparedStatement stmt = connection.prepareStatement(query)){

			stmt.setInt(1,userId);
			stmt.setInt(2,otherUserId);
			final int addedToFavourites = stmt.executeUpdate();

			if(addedToFavourites > 0){
				return ("Added to favourites");
			}

		} catch (SQLException exception) {
			logger.error("failed to add favourites user id={} other user id={} ", userId, otherUserId, exception);
			return exception.toString();
		}

		return ("Not added to favourites");
	}

	public String removeFromFavourites(final int userId, final int otherUserId){
		final String query = "DELETE FROM \"smapp_favourite_users\" WHERE user_id = ? AND favourite_user_id = ? ";
		try(final Connection connection = dataSource.getConnection();
			final PreparedStatement stmt = connection.prepareStatement(query)){

			stmt.setInt(1,userId);
			stmt.setInt(2,otherUserId);
			final int removedFromFavourites = stmt.executeUpdate();

			if(removedFromFavourites > 0){
				return ("Removed from favourites");
			}

		} catch (SQLException exception) {
			logger.error("failed to remove from favourites user id={} other user id={} ", userId, otherUserId, exception);
			return exception.toString();
		}

		return ("Not removed from favourites");
	}

}
