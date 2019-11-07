package com.example.appdistancia.Model;


public class Project {
    private String idProject;
    private String name;
    private String description;
    private String idUser;
    private double meters;
    private String resistencia;


    public Project(String idProject, String name, String description, double meters, String resistencia) {
        this.idProject = idProject;
        this.name = name;
        this.description = description;
        this.meters = meters;
        this.resistencia = resistencia;
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
        this.description = description;
        this.meters = meters;
        this.resistencia = resistencia;
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

    public double getMeters() {
        return meters;
    }

    public void setMeters(double meters) {
        this.meters = meters;
    }

    public String getResistencia() {
        return resistencia;
    }

    public void setResistencia(String resistencia) {
        this.resistencia = resistencia;
    }

    @Override
    public String toString() {
        return name;
    }


}
