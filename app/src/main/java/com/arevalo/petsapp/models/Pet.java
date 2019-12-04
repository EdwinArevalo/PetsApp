package com.arevalo.petsapp.models;

public class Pet {

    private Long petid;
    private String petname, petrace, petage, peruser, petimage;

    public Pet(Long petid, String petname, String petrace, String petage, String peruser) {
        this.petid = petid;
        this.petname = petname;
        this.petrace = petrace;
        this.petage = petage;
        this.peruser = peruser;
    }

    public Pet(Long petid, String petname, String petrace, String petage, String peruser, String petimage) {
        this.petid = petid;
        this.petname = petname;
        this.petrace = petrace;
        this.petage = petage;
        this.peruser = peruser;
        this.petimage = petimage;
    }

    public Long getPetid() {
        return petid;
    }

    public void setPetid(Long petid) {
        this.petid = petid;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getPetrace() {
        return petrace;
    }

    public void setPetrace(String petrace) {
        this.petrace = petrace;
    }

    public String getPetage() {
        return petage;
    }

    public void setPetage(String petage) {
        this.petage = petage;
    }

    public String getPeruser() {
        return peruser;
    }

    public void setPeruser(String peruser) {
        this.peruser = peruser;
    }

    public String getPetimage() {
        return petimage;
    }

    public void setPetimage(String petimage) {
        this.petimage = petimage;
    }
}
