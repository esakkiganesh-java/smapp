package com.twozo.app.model.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageResponseDto {
    private int id;
    private String receiverName;
    private String content;
    private String senderName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String sentTimeStamp;
    private String status;

    public MessageResponseDto(final int id,final String receiverName,final String content,final String senderName,final String sentTimeStamp,final String status){
        this.id = id;
        this.receiverName = receiverName;
        this.content = content;
        this.senderName = senderName;
        this.sentTimeStamp = sentTimeStamp;
        this.status = status;
    }

    public int getMessageId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setTimeStamp(final String timeStamp) {
        this.sentTimeStamp = timeStamp;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getMessageContent() {
        return content;
    }

    public String getSentTimeStamp() {
        return sentTimeStamp;
    }

    public void setMessageId(final int id) {
        this.id = id;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(final String Status){
        this.status = status;
    }
}


