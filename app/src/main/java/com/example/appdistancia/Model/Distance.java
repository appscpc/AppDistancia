package com.example.appdistancia.Model;

public class Distance {
    private String idDistance;
    private String objectA;
    private String objectB;
    private Double distance;
    private int veces;
    private String idProject;

    public Distance(String objectA, String objectB, Double distance, int veces) {
        this.idDistance = idDistance;
        this.objectA = objectA;
        this.objectB = objectB;
        this.distance = distance;
        this.veces = veces;
        this.idProject = idProject;
    }

    public Distance() {
        this.idDistance = idDistance;
        this.objectA = objectA;
        this.objectB = objectB;
        this.distance = distance;
        this.veces = veces;
        this.idProject = idProject;
    }


    public String getIdDistance() {
        return idDistance;
    }

    public void setIdDistance(String idDistance) {
        this.idDistance = idDistance;
    }

    public String getObjectA() {
        return objectA;
    }

    public void setObjectA(String objectA) {
        this.objectA = objectA;
    }

    public String getObjectB() {
        return objectB;
    }

    public void setObjectB(String objectB) {
        this.objectB = objectB;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getVeces() {
        return veces;
    }

    public void setVeces(int veces) {
        this.veces = veces;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }
}
