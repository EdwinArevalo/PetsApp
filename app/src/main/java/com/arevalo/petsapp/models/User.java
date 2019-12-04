package com.arevalo.petsapp.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Long userid;
    private String usernames;
    private String useremail;
    private String userpassword;
    private List<Pet> pets = new ArrayList<>();

    public User(Long userid, String usernames, String useremail, String userpassword) {
        this.userid = userid;
        this.usernames = usernames;
        this.useremail = useremail;
        this.userpassword = userpassword;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsernames() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames = usernames;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
