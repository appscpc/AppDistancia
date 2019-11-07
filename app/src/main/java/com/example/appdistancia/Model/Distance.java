package com.example.appdistancia.Model;

public class Distance {
    private String idDistance;
    private String objectA;
    private String objectB;
    private double distance;
    private String idProject;
    private String restrictions;

    public Distance(String objectA, String objectB, double distance, String restrictions) {
        this.idDistance = idDistance;
        this.objectA = objectA;
        this.objectB = objectB;
        this.distance = distance;
        this.idProject = idProject;
        this.restrictions = restrictions;
    }

    public Distance() {
        this.idDistance = idDistance;
        this.objectA = objectA;
        this.objectB = objectB;
        this.distance = distance;
        this.idProject = idProject;
        this.restrictions = restrictions;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }
}
