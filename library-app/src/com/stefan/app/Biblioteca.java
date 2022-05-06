package com.stefan.app;

import java.util.*;
import java.text.*;
public class Biblioteca{
    private List<Client> clienti = new LinkedList<>();
    private List<Carte> carti = new LinkedList<>();

    private Biblioteca(){}

    private static final Biblioteca INSTANCE = new Biblioteca();

    public static Biblioteca getInstance(){
        return INSTANCE;
    }

    public void adaugaStudent(String nume, String facultate, int anStudiu) {
        try{
            for(Client c: clienti){
                if(c.getNume().equals(nume)){
                    throw new NumeDejaExistentException("Nu se poate adauga, numele este deja inregistrat!");
                }
            }
            Student s = new Student(nume, facultate, anStudiu); 
            clienti.add(s);
            System.out.println("Studentul "+s.getNume()+" a fost adaugat! Cod client: "+s.getCodClient());
        }catch (NumeDejaExistentException e){
            System.out.println(e);
        }
    }

    public void adaugaProfesor(String nume, String materiePredata){
        try{
            for(Client c: clienti){
                if(c.getNume().equals(nume)){
                    throw new NumeDejaExistentException("Nu se poate adauga, numele este deja inregistrat!");
                }
            }
            Profesor p = new Profesor(nume, materiePredata);
            clienti.add(p);
            System.out.println("Profesorul "+p.getNume()+" a fost adaugat! Cod client: "+p.getCodClient());
        }catch (NumeDejaExistentException e){
            System.out.println(e);
        }
    }

    public void afiseazaClienti(){
        if(!clienti.isEmpty()){
            System.out.println("Clientii bibliotecii in ordinea inregistrarii sunt: ");
            clienti.forEach(System.out::println);
        }else{
            System.out.println("Nu exista clienti in lista bibliotecii!");
        }
    }

    public void afiseazaStudenti(){
        System.out.println("Doar clientii bibliotecii care sunt studenti: ");
        for(Client c: clienti){
            if(c instanceof Student){
                System.out.println(c);
            }
        }
    }

    public void adaugaCarte( String titlu, String isbn, String autor, String gen, int nrPagini){
        Carte carte = new Carte(titlu, isbn, autor, gen, nrPagini);
        boolean isbnInStoc = false;
        boolean titluInStoc = false;
        for(int i=0;i<carti.size();i++){
            if(carti.get(i).getIsbn().equals(isbn)){
                isbnInStoc = true;
            }else if(carti.get(i).getTitlu().equals(titlu)){
                titluInStoc = true;
            }
        }

        if(!isbnInStoc && !titluInStoc){
            System.out.println("A fost adaugat primul exemplar al cartii "+carte.getTitlu()+
                "   nr exemplare: "+carte.getNrDisponibile());
            carti.add(carte);
        }else{
            for(Carte c: carti){
                if(c.getIsbn().equals(isbn) && c.getTitlu().equals(titlu)){
                    int plusDisponibile = c.getNrDisponibile()+1;
                    c.setNrDisponibile(plusDisponibile);
                    System.out.println("A fost adaugat inca un exemplar al cartii "+c.getTitlu()+
                        "   nr exemplare: "+c.getNrDisponibile());
                }else if(c.getIsbn().equals(isbn) && !c.getTitlu().equals(titlu)){
                    System.out.println("Exista o carte cu acest isbn, dar nume diferit!");
                }
            }
        }
    }

    public void afiseazaCarti(){
        if(!carti.isEmpty()){
            System.out.println("Toate cartile din colectia bibliotecii in ordinea inregistrarii sunt: ");
            carti.forEach(System.out::println);
        }else{
            System.out.println("Nu exista carti in lista bibliotecii!");
        }
    }

    public synchronized void afiseazaCartiDisponibile(){
        if(!carti.isEmpty()){
            boolean disponibile = false;
            for(int i=0;i<carti.size();i++){
                if(carti.get(i).getNrDisponibile()>0){
                    disponibile = true;
                    break;
                }
            }

            if(disponibile){
                System.out.println("Cartile disponibile in biblioteca sunt: ");
                for(Carte c: carti){
                    if(c.getNrDisponibile()>0){
                        System.out.println(c);
                    }
                }
            }else{
                System.out.println("Nu exista carti disponibile!");
            }
        }else{
            System.out.println("Nu exista carti in lista bibliotecii!");
        }
    }

    public void cautaCarte(String titlu){
        boolean carteaExista = false;
        for(Carte c: carti){
            if(c.getTitlu().equals(titlu)){
                carteaExista = true;
                System.out.println("Detalii carte cautata: "+"\n"+c);
                break;
            }
        }

        if(!carteaExista){
            System.out.println("Cartea cautata nu exista!");
        }
    }

    public void filtreazaCartiDupaGen(String gen){
        System.out.println("Carti din genul: "+gen);
        for(Carte c: carti){
            if(c.getGen().equals(gen)){
                System.out.println(c);
            }
        }
    }

    public void sorteazaCarti(){
        List<Carte> listaCarti = new ArrayList<>(this.carti);
        Comparator<Carte> byNrPagini = (Carte c1, Carte c2)->c1.getNrPagini()-c2.getNrPagini();
        Collections.sort(listaCarti, byNrPagini);
        System.out.println("Carti sortate crescator dupa nr pagini: ");
        listaCarti.forEach(e->System.out.println(e));
    }    

    public void sorteazaClienti(){
        List<Client> listaClienti = new ArrayList<>(this.clienti);
        Comparator<Client> byNume = (Client c1, Client c2)->c1.getNume().compareTo(c2.getNume());
        Collections.sort(listaClienti, byNume);
        System.out.println("Clienti sortati lexicografic: ");
        listaClienti.forEach(e->System.out.println(e));
    }

    public void celMaiFidelCititor(){
        List<Client> listaClienti = new ArrayList<>(this.clienti);
        Client clientFidel = Collections.max(listaClienti, Comparator.comparing((c)->c.getCartiImprumutate().size()));
        long count = clienti.stream().filter(s->s.getCartiImprumutate().size()==clientFidel.getCartiImprumutate().size()).count();
        if(count>1 && clientFidel.getCartiImprumutate().size()>0){
            System.out.println("Clientii abonati actual care au citit cele mai multe carti (in nr egal) sunt "+count+" : ");
            for(Client c: listaClienti){
                if(c.getCartiImprumutate().size()==clientFidel.getCartiImprumutate().size()){
                    System.out.println(c.getNume()+"   Nr carti citite: "+c.getCartiImprumutate().size());
                }
            }
        }else if(clientFidel.getCartiImprumutate().size()>0){
            System.out.println("Cel mai fidel cititor dintre abonatii actuali este: "+clientFidel.getNume()+
                "   Nr carti citite: "+clientFidel.getCartiImprumutate().size()); 
        }else{
            System.out.println("Nu au fost imprumutate carti de catre abonatii actuali!");
        }
    }

    public void imprumutaCarte(String isbn)throws ParseException{
        if(carti.isEmpty()){
            System.out.println("Nu se poate apela imprumutaCarte(), nu au fost adaugate carti!");
        }else if(clienti.isEmpty()){
            System.out.println("Nu se poate apela imprumutaCarte(), nu au fost adaugati clienti!");
        }else{
            try{
                System.out.println("Pt imprumutare se verifica in stoc codul introdus ...");
                boolean inStoc = false;
                int indiceCarte = 0;
                for(int i=0;i<carti.size();i++){
                    if(carti.get(i).getIsbn().equals(isbn)){
                        inStoc = true;
                        indiceCarte = i;
                        break;
                    }
                }

                for(Carte c: carti){
                    if(!inStoc){
                        throw new CarteIndisponibilaException("Cartea cu acest isbn nu exista in biblioteca!");
                    }else if(c.getIsbn().equals(isbn) && c.getNrDisponibile()==0){
                        throw new CarteIndisponibilaException("Cartea este indisponibila/imprumutata!");
                    }
                }

                System.out.println("Cartea este in stoc, introduceti codul clientului pt imprumutare... ");
                Scanner scanner = new Scanner(System.in);
                int codClientIntrodus= Integer.parseInt(scanner.nextLine());

                boolean codClientExistent = false;
                int indiceClient = 0;
                for(int i=0;i<clienti.size();i++){
                    if(clienti.get(i).getCodClient() == codClientIntrodus){                        
                        codClientExistent = true;
                        indiceClient = i;
                        break;
                    }
                }

                if(codClientExistent && clienti.get(indiceClient).getImprumutataCurent()!=null){
                    System.out.println("Are deja carte imprumutata!");
                    scanner.close();
                }else if(codClientExistent && clienti.get(indiceClient).getImprumutataCurent()==null){
                    System.out.println("Introduceti data de retur sub forma 'zz/ll/aaaa' !");
                    String dataReturIntrodusa = scanner.nextLine();
//                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
//                    Date dRI = formatter.parse(dataReturIntrodusa);
                    scanner.close();

                    Client clientImprumutat = clienti.get(indiceClient);
                    carti.get(indiceCarte).addToIstoricClienti(clientImprumutat);
                    carti.get(indiceCarte).addToImprumutatiActual(clientImprumutat);
                    int minusDisponibile = carti.get(indiceCarte).getNrDisponibile()-1;
                    carti.get(indiceCarte).setNrDisponibile(minusDisponibile);
                    System.out.println("Clientul "+clientImprumutat.getNume()+", cod client "+clientImprumutat.getCodClient()+
                        ", a imprumutat cartea: "+carti.get(indiceCarte).getTitlu());

                    Carte carteDeImprumutat = carti.get(indiceCarte);
                    clienti.get(indiceClient).setImprumutataCurent(carteDeImprumutat);
                    clienti.get(indiceClient).setDataRetur(dataReturIntrodusa);
                    clienti.get(indiceClient).addToCartiImprumutate(carteDeImprumutat);
                }else{
                    System.out.println("Nu exista un client cu acest cod!");
                }
            }catch (CarteIndisponibilaException e){
                System.out.println(e);
            }
        }
    }

    public void returneazaCarte(String isbn)throws ParseException{
        if(clienti.isEmpty()){
            System.out.println("Nu se poate apela returnareCarte().Nu exista clienti adaugati in biblioteca!");    
        }else{
            List<Client> imprumutatiActual = new ArrayList<>();
            for(int i=0;i<clienti.size();i++){
                if(clienti.get(i).getImprumutataCurent()!=null && clienti.get(i).getImprumutataCurent().getIsbn().equals(isbn)){
                    imprumutatiActual.add(clienti.get(i));
                }
            }
            boolean eImprumutata = !imprumutatiActual.isEmpty();

            boolean inStoc = false;
            for(int i=0;i<carti.size();i++){
                if(carti.get(i).getIsbn().equals(isbn)){
                    inStoc = true;
                    break;
                }
            }

            if(eImprumutata){
                System.out.println("Cartea pt returnare este imprumutata de catre: ");
                imprumutatiActual.forEach(e->System.out.println(e.getNume()+"   cod client: "+e.getCodClient()));
                Scanner scanner = new Scanner(System.in);
                System.out.println("Introduceti codul clientului care returneaza cartea pt finalizarea returului...");
                String codClientIntrodus = scanner.nextLine().trim();
                scanner.close();
                int codClientImprumutat = Integer.parseInt(codClientIntrodus);

                Client clientActual = null;
                for(Carte c: carti){
                    if(c.getIsbn().equals(isbn)){
                        for(int i=0;i<c.getImprumutatiActual().size();i++){
                            if(clienti.get(i).getCodClient()==codClientImprumutat){
                                clientActual = clienti.get(i);
                                break;
                            }
                        }
                    }
                }

                boolean codClientCorect = false;
                for(Client c: imprumutatiActual){
                    if(c.getCodClient()==codClientImprumutat){
                        codClientCorect = true;
                    }
                }

                if(codClientCorect){
                    if(inStoc){
                        for(int i=0;i<carti.size();i++){
                            if(carti.get(i).getIsbn().equals(isbn)){
                                int plusDisponibile = carti.get(i).getNrDisponibile() +1;
                                carti.get(i).setNrDisponibile(plusDisponibile);
                                carti.get(i).removeFromImprumutatiActual(clientActual);
                                break;
                            }
                        }

                    }else if(!inStoc){
                        System.out.println("Cartea nu mai este in lista bibliotecii (a fost stearsa) , "+
                            "acum fiind returnata de catre client, va fi readaugata in lista."+
                            " Pt. stergerea definitiva reapelati stergeCarte()!");
                        for(Client c: clienti){
                            if(c.getImprumutataCurent()!=null && c.getImprumutataCurent().getIsbn().equals(isbn) && c.getCodClient()==codClientImprumutat){
                                System.out.println("Adaugare carte returnata in lista bibliotecii: "+c.getImprumutataCurent().getTitlu());
                                c.getImprumutataCurent().setNrDisponibile(1);
                                c.getImprumutataCurent().removeFromImprumutatiActual(c);
                                carti.add(c.getImprumutataCurent());
                                break;
                            }
                        }
                    }

                    for(Client c: clienti){
                        if(c.getImprumutataCurent()!=null && c.getImprumutataCurent().getIsbn().equals(isbn) && c.getCodClient()==codClientImprumutat){
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                            String dataAcum = formatter.format(new Date());
                            Date dA = formatter.parse(dataAcum);
                            String dataRetur = c.getDataRetur();
                            Date dR = formatter.parse(dataRetur);
                            int verificareDataRetur = dR.compareTo(dA);
                            if(verificareDataRetur<0){
                                System.out.println(c.getNume()+" a depasit data de returnare a cartii!");
                                c.addPenalitati();
                            }
                            c.clearDataRetur();
                            System.out.println(c.getNume()+" a returnat cartea "+c.getImprumutataCurent().getTitlu());
                            c.clearImprumutataCurent();
                            break;
                        }
                    }
                }else{
                    System.out.println("A fost introdus un cod client gresit!");
                }
            }else if(!eImprumutata){
                if(inStoc){
                    System.out.println("S-a incercat returnarea unei carti ce nu este imprumutata"+
                        "de catre nici un abonat actual al bibliotecii!");
                }else{
                    System.out.println("Nu exista in biblioteca o carte cu isbn-ul introdus!");
                }
            }
        }
    }

    public void stergeCarte(String titlu){
        if(!carti.isEmpty()){
            boolean inStoc = false;
            for(int i=0;i<carti.size();i++){
                if(carti.get(i).getTitlu().equals(titlu)){
                    inStoc = true;
                    break;
                }
            }

            if(inStoc){
                for(int i=0;i<carti.size();i++){
                    if(carti.get(i).getTitlu().equals(titlu) && !carti.get(i).getImprumutatiActual().isEmpty()){
                        System.out.println("Cartea este imprumutata, daca o stergeti acum, cand va fi returnata de catre" +
                            " client, va fi adaugata in lista bibliotecii!");
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Introduceti STERG pt stergere sau ASTEPT pt asteptare retur!");
                        String comanda = scanner.nextLine();
                        scanner.close();
                        if(comanda.equalsIgnoreCase("STERG")){
                            if(carti.get(i).getTitlu().equals(titlu)){                        
                                System.out.println("Cartea "+carti.get(i).getTitlu()+" a fost stearsa din lista bibliotecii!");
                                carti.remove(i);
                                break;
                            }
                        }else if(comanda.equalsIgnoreCase("ASTEPT")){
                            System.out.println("S-a anulat stergerea cartii. Se asteapta returnarea !");
                        }
                        break;
                    }else if(carti.get(i).getTitlu().equals(titlu)){
                        carti.remove(i);
                        System.out.println("Cartea "+titlu+" a fost stearsa din lista bibliotecii!");
                        break;
                    }
                }
            }else{
                System.out.println("Nu exista o carte cu acest titlu in lista bibliotecii!");
            }
        }else{
            System.out.println("Nu exista carti in liste bibliotecii!");
        }
    }

    public void stergeClient(String nume){
        if(!clienti.isEmpty()){
            boolean clientExistent = false;
            for(int i=0;i<clienti.size();i++){
                if(clienti.get(i).getNume().equals(nume)){
                    clientExistent = true;
                    break;
                }
            }

            if(clientExistent){
                for(int i=0;i<clienti.size();i++){
                    if(clienti.get(i).getNume().equals(nume) && clienti.get(i).getImprumutataCurent()!=null){
                        System.out.println("Clientul "+nume+" are carte imprumutata. Daca il stergeti, exemplarul "+
                            "va ramane la client !");
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Introduceti STERG pt stergere sau ASTEPT pt asteptare retur!");
                        String comanda = scanner.nextLine();
                        scanner.close();
                        if(comanda.equalsIgnoreCase("STERG")){
                            if(clienti.get(i).getNume().equals(nume)){
                                clienti.get(i).getImprumutataCurent().removeFromImprumutatiActual(clienti.get(i));
                                clienti.remove(i);
                                System.out.println("Clientul "+nume+" a fost sters din abonatii bibliotecii!");
                            }
                        }else if(comanda.equalsIgnoreCase("ASTEPT")){
                            System.out.println("S-a anulat stergerea clientului. Se asteapta returnarea cartii !");
                        }
                        break;
                    }else if(clienti.get(i).getNume().equals(nume)){
                        clienti.remove(i);
                        System.out.println("Clientul "+nume+" a fost sters din abonatii bibliotecii!");
                        break;
                    }
                }
            }else{
                System.out.println("Nu exista un client cu acest nume!");    
            }
        }else{
            System.out.println("Nu exista clienti adaugati!");
        }
    }

    public void arePenalitati(String nume)throws ParseException{
        if(!clienti.isEmpty()){
            int indiceClient = 0;
            boolean clientExistent = false;            
            for(int i=0;i<clienti.size();i++){
                if(clienti.get(i).getNume().equals(nume)){
                    indiceClient = i;
                    clientExistent = true;
                    break;
                }
            }

            if(clientExistent){
                if(clienti.get(indiceClient).getDataRetur()!=null){
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                    String dataAcum = formatter.format(new Date());
                    Date dA = formatter.parse(dataAcum);
                    String dataRetur = clienti.get(indiceClient).getDataRetur();
                    Date dR = formatter.parse(dataRetur);
                    if(dR.compareTo(dA)<0){
                        System.out.println(clienti.get(indiceClient).getNume()+
                            " a depasit data de returnare a cartii imprumutate curent ! data retur era: "+clienti.get(indiceClient).getDataRetur());
                    }else{
                        System.out.println(clienti.get(indiceClient).getNume()+
                            " nu a depasit data de returnare a cartii imprumutate curent ! data retur este: "+clienti.get(indiceClient).getDataRetur());
                    }
                }else{
                    if(clienti.get(indiceClient).getPenalitati()>0){
                        System.out.println(clienti.get(indiceClient).getNume()+"  are penalitati pt imprumutari anterioare!");
                    }else{
                        System.out.println(clienti.get(indiceClient).getNume()+"  nu are penalitati!");
                    }
                }
            }else{
                System.out.println("Nu exista un client cu acest nume!");
            }
        }else{
            System.out.println("Nu exista clienti in lista bibliotecii!");
        }
    }

    public void verificareIstoricCarte(String titlu){
        if(!carti.isEmpty()){
            boolean inStoc = false;
            for(int i=0;i<carti.size();i++){
                if(carti.get(i).getTitlu().equals(titlu)){
                    inStoc = true;
                    break;
                }
            }

            if(inStoc){
                for(Carte c: carti){
                    if(c.getTitlu().equals(titlu) && c.getIstoricClienti()!=null){
                        System.out.println("Numarul proprietarilor anteriori ai cartii "+c.getTitlu()+" este: "+c.getIstoricClienti().size());
                        System.out.println("Acestia au fost: ");
                        c.getIstoricClienti().forEach(e->System.out.println(e.getNume()));
                        break;
                    }else if(c.getTitlu().equals(titlu) && c.getIstoricClienti()==null){
                        System.out.println("Nu are istoric. Nu a fost inca imprumutata!");
                        break;
                    }
                }
            }else{
                System.out.println("Nu exista o carte cu titlul introdus!");
            }
        }
    }
}

