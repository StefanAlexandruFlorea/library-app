package com.stefan.app;

public class Student extends Client{
    private String facultate;
    private int anStudiu;
    
    public Student(String nume, String facultate, int anStudiu){
        super(nume);
        this.facultate = facultate;
        this.anStudiu = anStudiu;        
    }
    

    public String getFacultate() {
        return facultate;
    }

    public int getAnStudiu() {
        return anStudiu;
    }


    @Override
    public String toString(){
        return super.getNume()+"   facultate: "+this.facultate+"   an studiu: "+this.anStudiu+"   cod client: "+super.getCodClient();
    }

}