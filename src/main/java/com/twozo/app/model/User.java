package com.twozo.app.model;

public class User {

    private String phNo;
    private String name;
    private String password;

    public User(final String phNo,final String name,final String password) {

        this.phNo = phNo ;
        this.name = name;
        this.password = password;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(final String phNo) {
        this.phNo = phNo;
    }

    public String getName(){
        return name;
    }

    public void setName(final String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(final String password){
        this.password = password;
    }


}
