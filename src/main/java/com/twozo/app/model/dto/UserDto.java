package com.twozo.app.model.dto;

public class UserDto  {
    private int id;
    private String phNo;
    private String name;
    private String password;

    public UserDto(final int id,final String phNo,final String name) {
        this.id = id;
        this.phNo = phNo;
        this.name = name;
    }

    public UserDto(final int id,final String phNo,final String name,final String password) {
        this.id = id;
        this.phNo = phNo;
        this.name = name;
        this.password = password;
    }

    public UserDto(final String phNo,final String name,final String password){
        this.phNo = phNo;
        this.name = name;
        this.password = password;
    }

    public UserDto(final String phNo,final String name) {
        this.phNo = phNo;
        this.name = name;
    }

	public UserDto(final int id,final String password) {
            this.id = id;
            this.password = password;
    }

    public UserDto(final int id){
        this.id = id;
    }

	public UserDto(){

    }

    public String getPassword() {
        return password;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPhNo(final String phNo) {
        this.phNo = phNo;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPhNo() {
        return phNo;
    }

    public String getName() {
        return name;
    }

    public void setId(final int id){
        this.id = id;
    }

    }

