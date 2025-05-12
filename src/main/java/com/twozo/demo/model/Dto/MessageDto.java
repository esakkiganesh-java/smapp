package com.twozo.demo.model.Dto;

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


	
	public MessageDto(int receiverId,int senderId,String content,String sentTimeStamp) {
		this.receiverId = receiverId;
		this.senderId = senderId;
		this.content = content;
		this.sentTimeStamp = sentTimeStamp;
	}

	public MessageDto(int id,String receiverName,String content,String senderName,String sentTimestamp){
		this.id = id;
		this.receiverName = receiverName;
		this.content = content;
		this.senderName = senderName;
		this.sentTimeStamp = sentTimestamp;
	}
	
	public MessageDto(int senderId,int receiverId) {
		this.senderId = senderId;
		this.receiverId = receiverId;
	}
	
	
	public MessageDto(int id,String content) {
		this.id = id;
		this.content = content;
	}
	
	public MessageDto() {

	}
	
	public int getMessageId() {
		return id;
	}
	
	public String getSenderName() {
		return senderName;
	}
	
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public void setTimeStamp(String timeStamp) {
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
	
	public String getMessageContent() {
		return content;
	}
	
	public String getSentTimeStamp() {
		return sentTimeStamp;
	}

	public void setMessageId(int id) {
		this.id = id;
	}
}
