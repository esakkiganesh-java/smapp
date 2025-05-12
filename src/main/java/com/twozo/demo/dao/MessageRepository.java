package com.twozo.demo.dao;

import com.twozo.demo.model.Message;
import com.twozo.demo.model.Dto.MessageDto;
import com.twozo.demo.model.Dto.MessageResponseDto;
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
public class MessageRepository implements MessageDao {

	private final DataSource dataSource;

	public MessageRepository(DataSource dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public boolean addMessage(Message message) {
		String query = "INSERT INTO \"smapp_message_history\" (receiver_id, sender_id, content, sent_timestamp) VALUES (?, ?, ?, ?)";

	    try (Connection connection = dataSource.getConnection()) {
	        PreparedStatement stmt = connection.prepareStatement(query);

	        
	        stmt.setInt(1, message.getReceiverId());
	        stmt.setInt(2, message.getSenderId());
	        stmt.setString(3, message.getMessageContent());
	        stmt.setTimestamp(4, Timestamp.from(message.getSentTimestamp()));

	        int rowsInserted = stmt.executeUpdate();
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
	public boolean editMessage(MessageDto messageDto) {
		String query = "UPDATE \"smapp_message_history\" SET content = ? WHERE id = ?";
		try(Connection connection = dataSource.getConnection()){
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.setString(1, messageDto.getMessageContent());
			stmt.setInt(2, messageDto.getMessageId());
			
			int msgEdited = stmt.executeUpdate();
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
	public boolean deleteMessage(int messageId) {
		String query = "DELETE FROM \"smapp_message_history\"  WHERE id = ?";
		try(Connection connection = dataSource.getConnection()){
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.setInt(1, messageId);
			
            int msgDeleted = stmt.executeUpdate();
            
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
	public Collection<MessageResponseDto> getInboxMessages(int userId){
		Collection<MessageResponseDto> inboxMessages = new ArrayList<>() ;
		String query = "SELECT m.id AS message_id, m.sender_id, m.receiver_id, m.content AS message_content, m.sent_timestamp,m.status, " +
				"us.name AS sender_name, ur.name AS receiver_name " +
				"FROM \"smapp_message_history\" m " +
				"JOIN \"smapp_user\" us ON m.sender_id = us.id " +
				"JOIN \"smapp_user\" ur ON m.receiver_id = ur.id " +
				"WHERE m.sender_id = ? OR m.receiver_id = ? " +
				"ORDER BY m.sent_timestamp DESC";
		String updateQuery = "UPDATE \"smapp_message_history\" SET status = 'seen' WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(query);
			 PreparedStatement updatestmt  = connection.prepareStatement(updateQuery)) {

			stmt.setInt(1, userId);
			stmt.setInt(2, userId);

			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				int messageId = resultSet.getInt("message_id");
				int receiverId = resultSet.getInt("receiver_id");
				String receiverName = resultSet.getString("receiver_name");
				String messageContent = resultSet.getString("message_content");
				String senderName = resultSet.getString("sender_name");

				Timestamp sentTimestamp = resultSet.getTimestamp("sent_timestamp");
				String timestamp = sentTimestamp.toInstant().toString();
				String status = resultSet.getString("status");

				if(receiverId == userId && "delivered".equals(status)){
					updatestmt.setInt(1,messageId);
					updatestmt.executeUpdate();
					status = "seen";
				}

				MessageResponseDto messageDto = new MessageResponseDto(
						messageId,
						receiverName,
						messageContent,
						senderName,
						timestamp,
						status
				);

				inboxMessages.add(messageDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return inboxMessages;
	}

	@Override
	public Collection<MessageResponseDto> getChatHistory(MessageDto messageDto){
		
		 Collection<MessageResponseDto> chatHistory = new ArrayList<>();

		String query = "SELECT " +
		               "m.id AS message_id, " +
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

		        stmt.setInt(1, messageDto.getSenderId());
		        stmt.setInt(2, messageDto.getReceiverId());
		        stmt.setInt(3, messageDto.getReceiverId());
		        stmt.setInt(4, messageDto.getSenderId());

		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
		            int messageId = rs.getInt("message_id");
		            String content = rs.getString("message_content");
					Timestamp sentTimestamp = rs.getTimestamp("sent_timestamp");
					String timestamp = sentTimestamp.toInstant().toString();
		            String senderName = rs.getString("sender_name");
		            String receiverName = rs.getString("receiver_name");
					String status = rs.getString("status");

		            MessageResponseDto chat = new MessageResponseDto(messageId,receiverName,content,senderName,timestamp,status);
		            chatHistory.add(chat);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		 return chatHistory;
		}
}
