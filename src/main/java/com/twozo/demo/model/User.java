package com.twozo.demo.model;

public class User {

    private String phNo;
    private String name;
    private String password;

    public User(String phNo,String name,String password) {

        this.phNo = phNo ;
        this.name = name;
        this.password = password;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }


}
