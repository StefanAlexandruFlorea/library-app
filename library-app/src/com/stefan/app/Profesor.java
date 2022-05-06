package com.stefan.app;

public class Profesor extends Client{
    private String materiePredata;
    
    public Profesor(String nume, String materiePredata){
        super(nume);
        this.materiePredata = materiePredata;        
    }
    

    public String getMateriePredata() {
        return materiePredata;
    }
    


    @Override
    public String toString(){
        return super.getNume()+"   materie: "+this.materiePredata+"   cod client: "+super.getCodClient();
    }

}