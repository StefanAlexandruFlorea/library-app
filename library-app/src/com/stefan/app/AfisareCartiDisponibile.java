package com.stefan.app;


public class AfisareCartiDisponibile implements Runnable{
    Biblioteca biblioteca = Biblioteca.getInstance();
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(50000);
                biblioteca.afiseazaCartiDisponibile();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}