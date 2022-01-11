package com.example.exempleminim2laura.Models;

public class Repos {

    //Atributes
    public String name;
    public String lenguage;


    public Repos(String name, String lenguage){
        this.name=name;
        this.lenguage=lenguage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLenguage() {
        return lenguage;
    }

    public void setLenguage(String lenguage) {
        this.lenguage = lenguage;
    }
}
