package com.twozo.smapp.model;

public class InboxInfo {

    private int userId;
    private String userName;
    private int unreadMessageCount;

    public InboxInfo(final int userId, final String userName, final int unreadMessageCount) {
        this.userId = userId;
        this.userName = userName;
        this.unreadMessageCount = unreadMessageCount;
    }

    public InboxInfo(){ }

    public int getUserId() {
        return userId;
    }

    public String getUserName(){
        return userName;
    }

    public int getUnreadMessageCount(){
        return unreadMessageCount;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUnReadMessageCount(int unReadMessageCount) {
        this.unreadMessageCount = unReadMessageCount;
    }
}
