package com.twozo.smapp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageDto {
	private int id;
	private int senderId;
	private int receiverId;
	private String receiverName;
	private String content;
	private String senderName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String sentTimeStamp;

	public MessageDto(final int receiverId,final int senderId,final String content,final String sentTimeStamp) {
		this.receiverId = receiverId;
		this.senderId = senderId;
		this.content = content;
		this.sentTimeStamp = sentTimeStamp;
	}

	public MessageDto(final int id,final String receiverName,final String content,final String senderName,final String sentTimestamp){
		this.id = id;
		this.receiverName = receiverName;
		this.content = content;
		this.senderName = senderName;
		this.sentTimeStamp = sentTimestamp;
	}
	
	public MessageDto(final int senderId,final int receiverId) {
		this.senderId = senderId;
		this.receiverId = receiverId;
	}

	public MessageDto(final int id,final String content) {
		this.id = id;
		this.content = content;
	}

	public MessageDto(final int id){
		this.id = id;
	}
	
	public MessageDto() {

	}
	
	public int getId() {
		return id;
	}
	
	public String getSenderName() {
		return senderName;
	}
	
	public void setSenderId(final int senderId) {
		this.senderId = senderId;
	}
	
	public void setReceiverId(final int receiverId) {
		this.receiverId = receiverId;
	}
	
	public void setContent(final String content) {
		this.content = content;
	}

	public void setSentTimeStamp(final String timeStamp) {
		this.sentTimeStamp = timeStamp;
	}
	
	public String getReceiverName() {
		return receiverName;
	}
	
	public int getSenderId() {
		return senderId;
	}
	
	public int getReceiverId() {
		return receiverId;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getSentTimeStamp() {
		return sentTimeStamp;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
