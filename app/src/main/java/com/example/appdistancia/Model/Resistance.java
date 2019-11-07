package com.example.appdistancia.Model;

public class Resistance {
    private String idResistance;
    private String name;
    private String object;
    private double amount;

    public Resistance(){
        this.idResistance = idResistance;
        this.name = name;
        this.object = object;
        this.amount = amount;
    }

    public String getIdResistance() {
        return idResistance;
    }

    public void setIdResistance(String idResistance) {
        this.idResistance = idResistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
