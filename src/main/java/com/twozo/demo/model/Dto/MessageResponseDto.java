package com.twozo.demo.model.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageResponseDto {
    private int id;
    private String receiverName;
    private String content;
    private String senderName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String sentTimeStamp;
    private String status;

    public MessageResponseDto(int id,String receiverName,String content,String senderName,String sentTimeStamp,String status){
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


    public void setContent(String content) {
        this.content = content;
    }
    public void setTimeStamp(String timeStamp) {
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

    public void setMessageId(int id) {
        this.id = id;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String Status){
        this.status = status;
    }
}


