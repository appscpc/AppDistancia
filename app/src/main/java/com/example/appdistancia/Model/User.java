package com.example.appdistancia.Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String idUser;
    private String name;
    private String email;
    private String password;
    private List<String> proyectos;

    public User(String idUser, String name, String email, String password) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.proyectos = new ArrayList<String>();
    }

    public User(){
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.proyectos = new ArrayList<String>();
    }

    public boolean isNull(){
        if(name.equals("")&& email.equals("") && password.equals("")){
            return false;
        } else
            return true;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<String> proyectos) {
        this.proyectos = proyectos;
    }
}
