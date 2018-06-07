package com.example.eri.workers_managing.model;


import java.io.Serializable;

public class User  implements  Serializable {

    private String name;
    private String email;
    private String password;
    private String created_at;
    private String newPassword;
    private String token;
    private String proffesion;
    private String premission;
    private String start_job;
    private String end_job;
    private String gradiliste;



    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProffesion(String proffesion){
        this.proffesion=proffesion;
    }

    public String getProfession(){
        return proffesion;
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

    public void setPremission(String premission){
        this.premission=premission;
    }

    public String getPremission(){
        return premission;
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
