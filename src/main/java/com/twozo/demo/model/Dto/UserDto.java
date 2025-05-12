package com.twozo.demo.model.Dto;

public class UserDto  {
    private int id;
    private String phNo;
    private String name;
    private String password;

    public UserDto(int id,String phNo,String name, String password) {
        this.id = id;
        this.phNo = phNo;
        this.name = name;
        this.password = password;
    }

    public UserDto(String phNo,String name,String password){
        this.phNo = phNo;
        this.name = name;
        this.password = password;
    }

    public UserDto(String phNo,String name) {
        this.phNo = phNo;
        this.name = name;
    }

	public UserDto(int id,String password) {
            this.id = id;
            this.password = password;
    }

	public UserDto(){

    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public void setPassword(String password) {
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

    public void setUserId(int id){
        this.id = id;
    }

    }

