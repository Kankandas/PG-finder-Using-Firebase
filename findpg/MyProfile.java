package com.example.kankan.findpg;

public class MyProfile {
    private String name;
    private String locality;
    private String state;
    private String flat;
    private String url;
    private String district;
    private String sex;
    private String pincode;

    public MyProfile() {

    }

    public MyProfile(String name, String locality, String state, String flat, String url, String district, String sex, String pincode) {
        this.name = name;
        this.locality = locality;
        this.state = state;
        this.flat = flat;
        this.url = url;
        this.district = district;
        this.sex = sex;
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
