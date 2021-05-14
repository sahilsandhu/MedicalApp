package com.example.fp;

public class User {


    String name;
    String imageurl;
    String uid;
    String age;
    String yearsofexp;
    String specialization;

    public User()
    {}
    public User(String name, String imageurl, String uid, String age, String yearsofexp, String specialization) {

        this.name = name;
        this.imageurl = imageurl;
        this.uid = uid;
        this.age = age;
        this.yearsofexp = yearsofexp;
        this.specialization = specialization;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getYearsofexp() {
        return yearsofexp;
    }

    public void setYearsofexp(String yearsofexp) {
        this.yearsofexp = yearsofexp;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
