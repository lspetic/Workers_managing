package com.example.eri.workers_managing.model;


import java.io.Serializable;

public class User  implements  Serializable {

    private String name;
    private String email;
    private String password;
    private String created_at;
    private String newPassword;
    private String token;
    private String profession;

    private String start_job;
    private String end_job;
    private String gradiliste;
    private String surname;
    private String address;
    private String phone;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfession(String profession){
        this.profession = profession;
    }

    public String getProfession(){
        return profession;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStart_job(String start_job){
        this.start_job=start_job;
    }

    public String getStart_job(){return this.start_job;}

    public void setEnd_job(String end_job){
        this.end_job=end_job;
    }

    public String getEnd_job(){return this.end_job;}

    public void  setGradiliste(String gradiliste){
         this.gradiliste=gradiliste;
    }
    public String getGradiliste(){
        return this.gradiliste;
    }




}
