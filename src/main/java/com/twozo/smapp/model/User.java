package com.twozo.smapp.model;

public class User {
    private int id;
    private String phNo;
    private String name;
    private String password;

    public User(final String phNo,final String name,final String password) {
        this.phNo = phNo ;
        this.name = name;
        this.password = password;
    }

    public User(final int id,final String phNo,final String name) {
        this.id = id;
        this.phNo = phNo;
        this.name = name;
    }

    public User(final int id,final String phNo,final String name,final String password) {
        this.id = id;
        this.phNo = phNo;
        this.name = name;
        this.password = password;
    }

    public User(final String phNo,final String name) {
        this.phNo = phNo;
        this.name = name;
    }

    public User(final int id,final String password) {
        this.id = id;
        this.password = password;
    }

    public User(final int id){
        this.id = id;
    }

    public User(final String phNo){
        this.phNo = phNo;
    }

    public User(final String name,final int id){
        this.name = name;
        this.id = id;
    }

    public User(){

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

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
    }

}
