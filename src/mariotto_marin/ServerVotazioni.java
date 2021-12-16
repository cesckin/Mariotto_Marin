package mariotto_marin;
//import httpsample.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerVotazioni implements Runnable {
	Thread_Countdown tempo = new Thread_Countdown();

    public static void main(String[] args) {
        try {
        	
            ServerVotazioni sr = new ServerVotazioni(5000);
            Scanner kbReader = new Scanner(System.in);

            Thread t_sr = new Thread(sr);
            t_sr.start();

            kbReader.nextLine();
            sr.serverStop();
            t_sr.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }

    private ListaCandidati listacandidati;
    //    private final int port;
    private ServerSocket server;
    private volatile boolean t_continue;


    public ServerVotazioni(int port) throws IOException {
        this.listacandidati = new ListaCandidati();
        
//        this.port = port;
        this.server = new ServerSocket(port);
        this.t_continue = true;
        Candidato c1=new Candidato("Mariotto", "Francesco", "Partito Comunista", 0);
        listacandidati.add(c1);
        Candidato c2=new Candidato("Davide", "Bortuo", "Partito Fascista", 0);
        listacandidati.add(c2);
        Candidato c3=new Candidato("Chiave", "Americana", "Partito Liberalista", 0);
        listacandidati.add(c3);
        Candidato c4=new Candidato("Tappo", "Sughero", "Partito Conservatore", 0);
        listacandidati.add(c4);
        Candidato c5=new Candidato("Ruota", "Scorto", "Partito Moderato", 0);
        listacandidati.add(c5);
        
        int votiC1=0, votiC2=0, votiC3=0, votiC4=0, votiC5=0;
    }

    @Override
    public void run() {
    	while(!tempo.getTemposcaduto()) {
        try {
            Socket connessione = this.server.accept();

            ObjectInputStream in = new ObjectInputStream(connessione.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(connessione.getOutputStream());

            while (t_continue) {
                Object o = in.readObject();

                if (o instanceof Operazione.Operazione_t) {
                    Operazione.Operazione_t op = (Operazione.Operazione_t) o;

                    o = in.readObject();
                    if (o instanceof Candidato) {

                        switch (op) {
                            case Vota:
                            	//this.listacandidati.ricerca(c1);
                            	
                    	    	//Scanner scanner = new Scanner(System.in);
                    	        //int cvotato = scanner.nextInt();
                            	ArrayList<Candidato> candidati = this.listacandidati.getPersone();   
                            	Candidato c = null;
                                 c = candidati.get(((Candidato) o).getVoti());
                            	 c.addVoto();
                                break;
                            case Inserisci:
                            	//this.rubrica.elimina((Persona) o);
                            	//out.writeObject(Operazione.Operazione_t.Op_ACK);
                                break;
                            case Ricerca:
                                ListaCandidati res = this.listacandidati.ricerca((Candidato) o);
                                out.writeObject(res);
                                break;
                        }
                    } else {
                        System.out.println("[SERVER]: arrivato un oggetto inaspettato." + connessione);
                    }
                } else {
                    System.out.println("[SERVER]: non ho capito l'operazione." + connessione);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    	//System.out.println("[SERVER]: VOTAZIONI CONCLUSE");
    	serverStop();
    }
    public void serverStop() {
        this.t_continue = false;
    }
}
