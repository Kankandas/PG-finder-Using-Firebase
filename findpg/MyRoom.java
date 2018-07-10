package com.example.kankan.findpg;

public class MyRoom {
    String uriRoom;
    String uriHome;
    String typeOFRoom;
    String noOfBed;
    String stateName,distructName,localityName,picode;
    public MyRoom()
    {

    }

    public MyRoom(String uriRoom, String uriHome, String typeOFRoom, String noOfBed, String stateName, String distructName, String localityName, String picode) {

        this.uriRoom = uriRoom;
        this.uriHome = uriHome;
        this.typeOFRoom = typeOFRoom;
        this.noOfBed = noOfBed;
        this.stateName = stateName;
        this.distructName = distructName;
        this.localityName = localityName;
        this.picode = picode;
    }

    public String getUriRoom() {
        return uriRoom;
    }

    public void setUriRoom(String uriRoom) {
        this.uriRoom = uriRoom;
    }

    public String getUriHome() {
        return uriHome;
    }

    public void setUriHome(String uriHome) {
        this.uriHome = uriHome;
    }

    public String getTypeOFRoom() {
        return typeOFRoom;
    }

    public void setTypeOFRoom(String typeOFRoom) {
        this.typeOFRoom = typeOFRoom;
    }

    public String getNoOfBed() {
        return noOfBed;
    }

    public void setNoOfBed(String noOfBed) {
        this.noOfBed = noOfBed;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistructName() {
        return distructName;
    }

    public void setDistructName(String distructName) {
        this.distructName = distructName;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getPicode() {
        return picode;
    }

    public void setPicode(String picode) {
        this.picode = picode;
    }


}
