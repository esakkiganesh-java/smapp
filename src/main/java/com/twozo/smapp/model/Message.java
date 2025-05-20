package com.twozo.smapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public class Message {
    private int receiverId;
    private String content;
    private int senderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant sentTimestamp;


    public Message(final int receiverId,final String content,final int senderId,final Instant sentTimestamp) {
        this.receiverId = receiverId;
        this.content = content;
        this.senderId = senderId;
        this.sentTimestamp = sentTimestamp;
    }

    public int getReceiverId(){
        return receiverId;
    }

    public int getSenderId(){
        return senderId;
    }

    public String getContent(){
        return content;
    }

    public Instant getSentTimestamp(){
        return sentTimestamp;
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
}



