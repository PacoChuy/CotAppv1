package com.example.cotapp;

public class Roles {
    private int id;
    private  String Rol;

    public Roles(){

    }

    public Roles(int id,String Rol){
        this.id = id;
        this.Rol=Rol;

    }
    public void setId(int id) {
        this.id = id;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }

    @Override
    public  String toString() {
        return  Rol;

    }

}
