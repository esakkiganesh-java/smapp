package com.twozo.smapp.dao;

import com.twozo.smapp.model.InboxInfo;
import com.twozo.smapp.model.Message;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
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

	    try (final Connection connection = dataSource.getConnection()) {
	         final PreparedStatement stmt = connection.prepareStatement(query);
			 stmt.setInt(1, message.getReceiverId());
	         stmt.setInt(2, message.getSenderId());
	         stmt.setString(3, message.getContent());
			 stmt.setTimestamp(4, Timestamp.from(message.getSentTimestamp()));

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
	public boolean update(final Message message,final String updateType) {

		final String query = "UPDATE \"smapp_message_history\" SET content = ? WHERE id = ?";

		try(final Connection connection = dataSource.getConnection()){
			final PreparedStatement stmt = connection.prepareStatement(query);
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

		try(final Connection connection = dataSource.getConnection()){
			final PreparedStatement stmt = connection.prepareStatement(query);
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
	public Collection<InboxInfo> getInbox(final int userId){
		final Collection<InboxInfo> inbox = new ArrayList<>();
		final String query =
				        "SELECT " +
						"    u.id , u.name , " +
						"    COUNT(m.id) AS unread_count, " +
						"    MAX(m.sent_timestamp) AS last_message_time, " +
						"    CASE " +
						"        WHEN f.user_id IS NOT NULL THEN 1 " +
						"        ELSE 0 " +
						"    END AS is_favourite " +
						"FROM \"smapp_message_history\" m " +
						"JOIN \"smapp_user\" u ON u.id = m.sender_id " +
						"LEFT JOIN \"smapp_favourite_users\" f " +
						"    ON f.user_id = ? " +
						"    AND f.favourite_user_id = u.id " +
						"WHERE m.receiver_id = ? " +
						"AND m.status = 'delivered' " +
						"GROUP BY u.id, u.name, f.user_id " +
						"ORDER BY is_favourite DESC, last_message_time DESC";

		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			final ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				final int id = resultSet.getInt("id");
				final String name = resultSet.getString("name");
				final int unReadMessageCount = resultSet.getInt("unread_count");

				final InboxInfo chatInfo = new InboxInfo(id,name,unReadMessageCount);
				inbox.add(chatInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return inbox;
	}

	@Override
	public Collection<Message> getChatHistory(final int senderId,final int receiverId){
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

		    try (final Connection connection = dataSource.getConnection();
		         final PreparedStatement stmt = connection.prepareStatement(query)){

		        stmt.setInt(1,senderId);
		        stmt.setInt(2,receiverId);
		        stmt.setInt(3,receiverId);
		        stmt.setInt(4,senderId);

		        final ResultSet resultSet = stmt.executeQuery();

		        while (resultSet.next()) {
					final int messageId = resultSet.getInt("message_id");
					final int receiver = resultSet.getInt("receiver_id");
					final String content = resultSet.getString("message_content");
					final int sender = resultSet.getInt("sender_id");
					final Timestamp sentTimestamp = resultSet.getTimestamp("sent_timestamp");
					final Instant timestamp = sentTimestamp.toInstant();
					final String senderName = resultSet.getString("sender_name");
					final String receiverName = resultSet.getString("receiver_name");
					final String status = resultSet.getString("status");

					final Message chat = new Message(messageId, receiver,receiverName,content, sender,senderName,timestamp,status);
		            chatHistory.add(chat);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		 return chatHistory;
	}

	public Collection<Message> getMessageReport(){
		final Collection<Message> messageReport = new ArrayList<>();
		final String query = " SELECT m.id,"+
				             " m.sender_id AS senderId,"+
				             " us.name AS senderName,"+
				             " m.receiver_id AS receiverId,"+
				             " ur.name AS receiverName,"+
		                     " m.content,m.sent_timestamp,m.status "+
				             " FROM \"smapp_message_history\" m "+
				             " JOIN \"smapp_user\" us ON us.id = m.sender_id "+
				             " JOIN \"smapp_user\" ur ON ur.id = m.receiver_id "+
				             " WHERE m.sent_timestamp > ? AND m.sent_timestamp < ? "+
				             " ORDER BY m.sent_timestamp ASC";

		try (final Connection connection = dataSource.getConnection();
		    final PreparedStatement stmt = connection.prepareStatement(query)){

			LocalDate firstDay = YearMonth.now().atDay(1);
			LocalDate lastDay = YearMonth.now().atEndOfMonth();

			Timestamp from = Timestamp.valueOf(firstDay.atTime(LocalTime.MIN));
			Timestamp to = Timestamp.valueOf(lastDay.atTime(LocalTime.MAX));

			stmt.setTimestamp(1, from);
			stmt.setTimestamp(2, to);

			final ResultSet resultSet = stmt.executeQuery();

			while(resultSet.next()){
				final int messageId = resultSet.getInt("id");
				final int senderId = resultSet.getInt("senderId");
				final String senderName = resultSet.getString("senderName");
				final int receiverId = resultSet.getInt("receiverId");
				final String receiverName = resultSet.getString("receiverName");
				final String content = resultSet.getString("content");
				final Timestamp timestamp = resultSet.getTimestamp("sent_timestamp");
				final Instant sent_timestamp = timestamp.toInstant();
				final String status = resultSet.getString("status");

				final Message message = new Message(messageId,receiverId,receiverName,content,senderId,senderName,sent_timestamp,status);
				messageReport.add(message);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return messageReport;
	}

	public void markMessagesAsSeen(final int userId) {
		final String query = "UPDATE \"smapp_message_history\" SET status = 'seen' WHERE receiver_id = ? AND status = 'delivered' ";

		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement updateStmt = connection.prepareStatement(query)) {

			 updateStmt.setInt(1, userId);
			 updateStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
