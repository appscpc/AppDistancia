package com.example.appdistancia.Model;

import java.util.ArrayList;

public class Project {
    private String idProject;
    private String name;
    private String description;
    private String idUser;

    //private ArrayList<Object> objects;
    //private ArrayList<Distance> distances;

    public Project(String idProject, String name, String description) {
        this.idProject = idProject;
        this.name = name;
        this.description = description;
        //this.objects = objects;
        //this.distances = distances;
    }

    public boolean isNull(){
        if(name.equals("")&& name.equals("")){
            return false;
        } else
            return true;
    }

    public Project(){
        this.idProject = idProject;
        this.name = name;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return name;
    }

    /* public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    public ArrayList<Distance> getDistances() {
        return distances;
    }

    public void setDistances(ArrayList<Distance> distances) {
        this.distances = distances;
    } */
}
