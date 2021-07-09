package com.example.android.quizbee.models;

public class Users {

    private String email;
    private String pass;
    private String name;
    private String orgName;

    public Users(){

    }

    public Users(String email, String pass, String name, String orgName) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.orgName = orgName;
    }


    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

}

