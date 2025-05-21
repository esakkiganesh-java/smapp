package com.twozo.smapp.dao;

import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class MessageDaoImpl implements MessageDao {

	private final DataSource dataSource;

	public MessageDaoImpl(final DataSource dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public boolean add(final Message message) {

		final String query = "INSERT INTO \"smapp_message_history\" (receiver_id, sender_id, content, sent_timestamp) VALUES (?, ?, ?, ?)";

	    try (Connection connection = dataSource.getConnection()) {
	        PreparedStatement stmt = connection.prepareStatement(query);

	        stmt.setInt(1, message.getReceiverId());
	        stmt.setInt(2, message.getSenderId());
	        stmt.setString(3, message.getContent());
	        stmt.setString(4, message.getSentTimestamp());

			final int rowsInserted = stmt.executeUpdate();

	        if(rowsInserted > 0) {
	        	return true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }

	 return false;
	}

	@Override
	public boolean update(final Message message,int updateType) {

		final String query = "UPDATE \"smapp_message_history\" SET content = ? WHERE id = ?";

		try(Connection connection = dataSource.getConnection()){
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.setString(1, message.getContent());
			stmt.setInt(2, message.getId());

			final int msgEdited = stmt.executeUpdate();

			if(msgEdited > 0) {
				return true;
			}

		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}

	 return false;
	}

	@Override
	public boolean delete(final Message message) {

		final String query = "DELETE FROM \"smapp_message_history\"  WHERE id = ?";

		try(Connection connection = dataSource.getConnection()){
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.setInt(1, message.getId());
			final int msgDeleted = stmt.executeUpdate();
            
            if(msgDeleted > 0) {
            	return true;
            }
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}

	 return false;
	}

	@Override
	public Collection<Message> getInbox(final int userId){

		final Collection<Message> inboxMessages = new ArrayList<>() ;

		final String query = "SELECT m.id AS message_id, m.sender_id, m.receiver_id, m.content AS message_content, m.sent_timestamp,m.status, " +
				"us.name AS sender_name, ur.name AS receiver_name " +
				"FROM \"smapp_message_history\" m " +
				"JOIN \"smapp_user\" us ON m.sender_id = us.id " +
				"JOIN \"smapp_user\" ur ON m.receiver_id = ur.id " +
				"WHERE m.sender_id = ? OR m.receiver_id = ? " +
				"ORDER BY m.sent_timestamp DESC";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				final int messageId = resultSet.getInt("message_id");
				final int receiverId = resultSet.getInt("receiver_id");
				final String receiverName = resultSet.getString("receiver_name");
				final String messageContent = resultSet.getString("message_content");
				final int senderId = resultSet.getInt("sender_id");
				final String senderName = resultSet.getString("sender_name");
				final Timestamp sentTimestamp = resultSet.getTimestamp("sent_timestamp");
				final String timestamp = sentTimestamp.toInstant().toString();
				String status = resultSet.getString("status");

				final Message messages = new Message(messageId, receiverId, receiverName, messageContent, senderId, senderName, timestamp, status);
				inboxMessages.add(messages);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return inboxMessages;
	}

	@Override
	public Collection<Message> getChatHistory(final Message message){

		final  Collection<Message> chatHistory = new ArrayList<>();

		final String query = "SELECT " +
		               "m.id AS message_id, " +
				       "m.sender_id AS sender_id, "+
				       "m.receiver_id AS receiver_id,"+
		               "m.content AS message_content, " +
		               "m.sent_timestamp,m.status, " +
		               "sender.name AS sender_name, " +
		               "receiver.name AS receiver_name " +
		               "FROM \"smapp_message_history\" m " +
		               "JOIN \"smapp_user\" sender ON m.sender_id = sender.id " +
		               "JOIN \"smapp_user\" receiver ON m.receiver_id = receiver.id " +
		               "WHERE (m.sender_id = ? AND m.receiver_id = ?) " +
		               "OR (m.sender_id = ? AND m.receiver_id = ?) " +
		               "ORDER BY m.sent_timestamp DESC";

		    try (Connection connection = dataSource.getConnection();
		         PreparedStatement stmt = connection.prepareStatement(query)){

		        stmt.setInt(1, message.getSenderId());
		        stmt.setInt(2, message.getReceiverId());
		        stmt.setInt(3, message.getReceiverId());
		        stmt.setInt(4, message.getSenderId());

		        ResultSet resultSet = stmt.executeQuery();

		        while (resultSet.next()) {
					final int messageId = resultSet.getInt("message_id");
					final int receiverId = resultSet.getInt("receiver_id");
					final String content = resultSet.getString("message_content");
					final int senderId = resultSet.getInt("sender_id");
					final Timestamp sentTimestamp = resultSet.getTimestamp("sent_timestamp");
					final String timestamp = sentTimestamp.toInstant().toString();
					final String senderName = resultSet.getString("sender_name");
					final String receiverName = resultSet.getString("receiver_name");
					final String status = resultSet.getString("status");

					final Message chat = new Message(messageId, receiverId,receiverName,content, senderId,senderName,timestamp,status);
		            chatHistory.add(chat);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		 return chatHistory;
	}

	public void markMessagesAsSeen(final int userId) {
		final String updateQuery = "UPDATE \"smapp_message_history\" SET status = 'seen' WHERE receiver_id = ? AND status = 'delivered' ";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

			 updateStmt.setInt(1, userId);
			 updateStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
