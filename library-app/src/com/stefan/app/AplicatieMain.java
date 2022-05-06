package com.stefan.app;
import java.util.*;

public class AplicatieMain{
    public static void main(String[] args) throws java.text.ParseException {
        Biblioteca biblioteca = Biblioteca.getInstance();
        
        AfisareCartiDisponibile a = new AfisareCartiDisponibile();
        Thread t = new Thread(a);
        t.start();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti una dintre comenzile exemplificate mai jos: "+"\n"+
                                "   adaugaStudent: nume/ facultate/ an studii"+"\n"+
                                "   adaugaProfesor: nume/ materie predata"+"\n"+
                                "   afiseazaClienti"+"\n"+
                                "   afiseazaStudenti"+"\n"+
                                "   adaugaCarte: titlu/ cod isbn/ autor/ gen/ nr pagini"+"\n"+
                                "   afiseazaCartiDisponibile"+"\n"+
                                "   cautaCarte: titlu"+"\n"+
                                "   filtreazaCartiDupaGen: gen"+"\n"+
                                "   sorteazaCarti"+"\n"+
                                "   sorteazaClienti"+"\n"+
                                "   celMaiFidelCititor"+"\n"+
                                "   imprumutaCarte: cod isbn"+"\n"+
                                "   returneazaCarte: cod isbn"+"\n"+
                                "   stergeCarte: titlu"+"\n"+
                                "   stergeClient: nume"+"\n"+
                                "   arePenalitati: nume"+"\n"+
                                "   verificareIstoricCarte: titlu"+"\n"+
                                "   exit"+"\n");
        
        while(true){
            String comanda = scanner.nextLine();
            String [] cuvinte = comanda.split(":|\\/");
            String[] cuv = new String[cuvinte.length];
            for(int i=0;i<cuv.length;i++){
                cuv[i]=cuvinte[i].trim();
            }
            
            switch(cuv[0]){
                case "exit": System.out.println("Aplicatia a fost inchisa!");
                			 scanner.close();
                             System.exit(0);
                case "adaugaStudent": biblioteca.adaugaStudent(cuv[1],cuv[2],Integer.parseInt(cuv[3]));
                                      break;  
                case "adaugaProfesor": biblioteca.adaugaProfesor(cuv[1],cuv[2]);                                       
                                       break;
                case "afiseazaClienti": biblioteca.afiseazaClienti();
                                        break; 
                case "afiseazaStudenti": biblioteca.afiseazaStudenti();
                                         break;    
                case "adaugaCarte": biblioteca.adaugaCarte(cuv[1],cuv[2],cuv[3],cuv[4], Integer.parseInt(cuv[5]));
                                    break;
                case "afiseazaCarti": biblioteca.afiseazaCarti();
                                      break;  
                case "afiseazaCartiDisponibile": biblioteca.afiseazaCartiDisponibile();
                                                 break;  
                case "cautaCarte": biblioteca.cautaCarte(cuv[1]);
                                   break; 
                case "filtreazaCartiDupaGen": biblioteca.filtreazaCartiDupaGen(cuv[1]);
                                        break;
                case "sorteazaCarti": biblioteca.sorteazaCarti();
                                        break; 
                case "sorteazaClienti": biblioteca.sorteazaClienti();
                                        break;  
                case "celMaiFidelCititor": biblioteca.celMaiFidelCititor();
                                        break;                       
                case "imprumutaCarte": biblioteca.imprumutaCarte(cuv[1]);
                                       break;
                case "retureazaCarte": biblioteca.returneazaCarte(cuv[1]);
                                       break;  
                case "stergeCarte": biblioteca.stergeCarte(cuv[1]);
                                    break;                       
                case "stergeClient": biblioteca.stergeClient(cuv[1]);
                                     break;                       
                case "arePenalitati": biblioteca.arePenalitati(cuv[1]);
                                      break;
                case "verificareIstoricCarte": biblioteca.verificareIstoricCarte(cuv[1]);
                                             break;
                default: System.out.println("Comanda inexistenta!");                              
            }
        }
    }
}