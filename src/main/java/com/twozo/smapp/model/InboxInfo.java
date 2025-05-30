package com.twozo.smapp.model;

public class InboxInfo {
    private int userId;
    private String userName;
    private int unReadMessageCount;

    public InboxInfo(final int userId, final String userName, final int unReadMessageCount) {
        this.userId = userId;
        this.userName = userName;
        this.unReadMessageCount = unReadMessageCount;
    }

    public InboxInfo(){ }

    public int getUserId() {
        return userId;
    }

    public String getUserName(){
        return userName;
    }

    public int getUnReadMessageCount(){
        return unReadMessageCount;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUnReadMessageCount(int unReadMessageCount) {
        this.unReadMessageCount = unReadMessageCount;
    }
}
