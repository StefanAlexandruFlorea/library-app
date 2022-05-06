package com.stefan.app;

import java.util.*;
public abstract class Client{
    private static int nextCodClient = 1;
    private int codClient;
    private String nume;
    private Carte imprumutataCurent;
    private ArrayList<Carte> cartiImprumutate = new ArrayList<>();
    private String dataRetur;
    private int penalitati;

    protected Client(String nume){
        this.nume = nume;
        this.codClient = nextCodClient;
        nextCodClient++;
    }
    
    
    protected int getCodClient() {
        return codClient;
    }

    protected String getNume() {
        return nume;
    }
    

    protected Carte getImprumutataCurent() {
        return imprumutataCurent;
    }
    
    protected void setImprumutataCurent(Carte imprumutataCurent) {
        this.imprumutataCurent = imprumutataCurent;
    }
    
    protected void clearImprumutataCurent() {
        this.imprumutataCurent = null;
    }
    

    protected ArrayList<Carte> getCartiImprumutate() {
        return cartiImprumutate;
    }
    
    protected void addToCartiImprumutate(Carte c) {
        this.cartiImprumutate.add(c);
    }
    
    
    protected String getDataRetur() {
        return dataRetur;
    }
    
    protected void setDataRetur(String dataRetur) {
        this.dataRetur = dataRetur;
    }
    
    protected void clearDataRetur() {
        this.dataRetur = null;
    }
    

    protected int getPenalitati() {
        return penalitati;
    }
    
    protected void addPenalitati() {
        this.penalitati++;
    }
    

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime*result + ((this.nume == null)? 0 :this.nume.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(o instanceof Client){
            Client c = (Client) o;
            return this.nume.equals(c.nume);
        }
        return false;
    }

}