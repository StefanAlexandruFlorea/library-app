package com.stefan.app;

import java.util.*;
public class Carte{
    private String titlu, isbn, autor, gen;
    private int nrPagini;
    private int nrDisponibile = 1;
    private Set<Client> istoricClienti = new LinkedHashSet<>();
    private List<Client> imprumutatiActual = new ArrayList<>();

    public Carte(String titlu, String isbn,String autor,String gen,int nrPagini){  
        this.titlu = titlu;
        this.isbn = isbn;
        this.autor = autor;
        this.gen = gen;
        this.nrPagini = nrPagini;
    } 
    

    public String getTitlu() {
        return titlu;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAutor() {
        return autor;
    }

    public String getGen() {
        return gen;
    }

    public int getNrPagini() {
        return nrPagini;
    }
    
    
    public int getNrDisponibile() {
        return nrDisponibile;
    }
    
    public void setNrDisponibile(int nrDisponibile) {
        this.nrDisponibile = nrDisponibile;
    }
    

    public Set<Client> getIstoricClienti() {
        return istoricClienti;
    }
    
    public void addToIstoricClienti(Client c) {
        this.istoricClienti.add(c);
    }
    

    public List<Client> getImprumutatiActual() {
        return imprumutatiActual;
    }
    
    public void addToImprumutatiActual(Client c) {
        this.imprumutatiActual.add(c);
    }
    
    public void removeFromImprumutatiActual(Client c) {
        this.imprumutatiActual.remove(c);
    }
    
    public void clearImprumutatiActual() {
        this.imprumutatiActual.clear();
    }
    

    @Override 
    public String toString(){
        return " titlu: "+this.titlu+"   isbn: "+this.isbn+"   autor: "+this.autor+
        "   gen: "+this.gen+"   nrPagini: "+this.nrPagini+"   imprumutate: "+this.imprumutatiActual.size()+
        "   disponibile: "+this.nrDisponibile;
    }
    
}  