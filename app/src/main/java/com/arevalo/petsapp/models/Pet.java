package com.arevalo.petsapp.models;

public class Pet {

    private Long petid, petuser;
    private String petname, petrace, petage, petimage;

    public Pet(Long petid, String petname, String petrace, String petage, Long petuser) {
        this.petid = petid;
        this.petname = petname;
        this.petrace = petrace;
        this.petage = petage;
        this.petuser = petuser;
    }

    public Pet(Long petid, String petname, String petrace, String petage, Long petuser, String petimage) {
        this.petid = petid;
        this.petname = petname;
        this.petrace = petrace;
        this.petage = petage;
        this.petuser = petuser;
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

    public Long getPetuser() {
        return petuser;
    }

    public void setPetuser(Long petuser) {
        this.petuser = petuser;
    }

    public String getPetimage() {
        return petimage;
    }

    public void setPetimage(String petimage) {
        this.petimage = petimage;
    }
}
