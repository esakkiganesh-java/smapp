package com.twozo.smapp.model;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Message {

    private int id;
    private int receiverId;
    private String content;
    private int senderId;
    private String senderName;
    private String receiverName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private Instant sentTimestamp;
    private MessageStatus status;

    public Message(final int receiverId, final String content, final int senderId, final Instant sentTimestamp){
        this.receiverId = receiverId;
        this.content = content;
        this.senderId = senderId;
        this.sentTimestamp = sentTimestamp;
    }

    public Message(final int id, final int receiverId, final String receiverName, final String content, final int senderId, final String senderName, final Instant sentTimestamp, final MessageStatus status){
        this.id = id;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.content = content;
        this.senderId = senderId;
        this.senderName = senderName;
        this.sentTimestamp = sentTimestamp;
        this.status = status;
    }

    public Message(final int senderId, final int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Message(final int id, final String content) {
        this.id = id;
        this.content = content;
    }

    public Message(final int id){
        this.id = id;
    }

    public Message(){}

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getContent(){
        return content;
    }

    public Instant getSentTimestamp(){
        return sentTimestamp;
    }

    public int getSenderId(){
        return senderId;
    }

    public int getReceiverId(){
        return receiverId;
    }

    public String getSenderName(){
        return senderName;
    }

    public String getReceiverName(){
        return receiverName;
    }

    public void setSenderName(String senderName){
        this.senderName = senderName;
    }

    public void setReceiverName(String receiverName){
        this.receiverName = receiverName;
    }

    public void setReceiverId(final int receiverId){
        this.receiverId = receiverId;
    }

    public void setSenderId(final int senderId){
        this.senderId = senderId;
    }

    public void setContent(final String messageContent){
        this.content = messageContent;
    }

    public void setSentTimestamp(final Instant sentTimestamp){
        this.sentTimestamp = sentTimestamp;
    }

    public MessageStatus getStatus(){
        return status;
    }

    public void setStatus(MessageStatus status){
        this.status = status;
    }
}



