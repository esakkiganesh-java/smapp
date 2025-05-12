package com.twozo.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public class Message {
    private int id;
    private int receiverId;
    private String content;
    private int senderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant sentTimestamp;


    public Message(int receiverId,String content,int senderId,Instant sentTimestamp) {
        this.receiverId = receiverId;
        this.content = content;
        this.senderId = senderId;
        this.sentTimestamp = sentTimestamp;
    }

    public int getMessageId(){
        return id;
    }

    public int getReceiverId(){
        return receiverId;
    }

    public int getSenderId(){
        return senderId;
    }

    public String getMessageContent(){
        return content;
    }

    public Instant getSentTimestamp(){
        return sentTimestamp;
    }

    public void setMessageId(int id){
        this.id = id;
    }

    public void setReceiverId(int receiverId){
        this.receiverId = receiverId;
    }

    public void setSenderId(int senderId){
        this.senderId = senderId;
    }

    public void setMessageContent(String messageContent){
        this.content = messageContent;
    }

    public void setSentTimestamp(Instant sentTimestamp){
        this.sentTimestamp = sentTimestamp;
    }
}



