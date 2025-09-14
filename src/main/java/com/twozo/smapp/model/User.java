package com.twozo.smapp.model;

public class User {

    private int id;
    private String phone;
    private String name;
    private String password;

    public User(final String phone, final String name, final String password) {
        this.phone = phone;
        this.name = name;
        this.password = password;
    }

    public User(final int id, final String phone, final String name) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }

    public User(final int id, final String phone, final String name, final String password) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.password = password;
    }

    public User(final String phone, final String name) {
        this.phone = phone;
        this.name = name;
    }

    public User(final int id, final String password) {
        this.id = id;
        this.password = password;
    }

    public User(final int id){
        this.id = id;
    }

    public User(final String phone){
        this.phone = phone;
    }

    public User(final String name,final int id){
        this.name = name;
        this.id = id;
    }

    public User(){}

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getName(){return name;}

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
