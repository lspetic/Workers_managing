package com.example.eri.workers_managing.model;

import java.io.Serializable;

public class Gradiliste implements Serializable{
    private String name;
    private String address;
    private String latlong;
    private Integer br_radnika;

    public Integer getBr_radnika() {
        return br_radnika;
    }

    public void setBr_radnika(Integer br_radnika) {
        this.br_radnika = br_radnika;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }
}
