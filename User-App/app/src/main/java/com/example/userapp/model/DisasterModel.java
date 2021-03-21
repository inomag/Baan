package com.example.userapp.model;

public class DisasterModel {

    private String coordinates;
    private String info;
    private int level;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(String coordinates){
        this.coordinates = coordinates;
    }
}
