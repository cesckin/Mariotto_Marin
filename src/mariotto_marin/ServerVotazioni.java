package mariotto_marin;
//import httpsample.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ServerVotazioni implements Runnable {
	Thread_Countdown tempo = new Thread_Countdown();

    public static void main(String[] args) {
        try {
        	
            ServerVotazioni sr = new ServerVotazioni(50000);
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
    private int tempoPerVotare=20000;
    private int tempoRestante=0;

    public ServerVotazioni(int port) throws IOException {
    	this.tempoPerVotare=tempoPerVotare;
    	this.tempoRestante=tempoRestante;
        this.listacandidati = new ListaCandidati();
        
        //this.port = port;
        this.server = new ServerSocket(port);
        this.t_continue = true;
        Candidato c1=new Candidato("Mariotto", "Francesco", "Partito Nordista", 0);
        listacandidati.add(c1);
        Candidato c2=new Candidato("Davide", "Bortuo", "Partito Fascista", 0);
        listacandidati.add(c2);
        Candidato c3=new Candidato("Chiave", "Americana", "Partito Liberalista", 0);
        listacandidati.add(c3);
        Candidato c4=new Candidato("Tappo", "Sughero", "Partito Conservatore", 0);
        listacandidati.add(c4);
        Candidato c5=new Candidato("Ruota", "Scorto", "Partito Moderato", 0);
        listacandidati.add(c5);
    }
	public void setTempoPerVotare(int tempoPerVotare) {
		this.tempoPerVotare = tempoPerVotare;
	}
	public void setTempoRestante(int tempoRestante) {
		this.tempoRestante = tempoRestante;
	}
	public int getTempoRestante() {
		return tempoRestante;
	}
    
    @Override
    public void run() {
    	
    	class Helper extends TimerTask{
    		int tempor=tempoPerVotare/1000;
		    public int i = 0;
		    public void run()
		    {
		    	if(i<=tempor) {
		    		setTempoRestante(tempor-i);
		    		i++;
		    	}else {
		    		
		    	}
		    }
		}
		
		Timer timer = new Timer();
		TimerTask task = new Helper();
		timer.schedule(task, 0, 1000);
    	
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
                            	//System.out.println("ciao");
                    	    	//Scanner scanner = new Scanner(System.in);
                    	        //int cvotato = scanner.nextInt();
                            	//ArrayList<Candidato> candidati = this.listacandidati.getPersone();   
                            	//Candidato c = null;
                                Candidato a = ((Candidato) o);
                                if(a.getNome().equals(listacandidati.getPersone().get(0).getNome())) {
                                	//System.out.println(listacandidati.getPersone().get(0));
                                	listacandidati.getPersone().get(0).addVoto();
                                }
                                if(a.getNome().equals(listacandidati.getPersone().get(1).getNome()))
                                	listacandidati.getPersone().get(1).addVoto();
                                if(a.getNome().equals(listacandidati.getPersone().get(2).getNome()))
                                	listacandidati.getPersone().get(2).addVoto();
                                if(a.getNome().equals(listacandidati.getPersone().get(3).getNome()))
                                	listacandidati.getPersone().get(3).addVoto();
                                if(a.getNome().equals(listacandidati.getPersone().get(4).getNome()))
                                	listacandidati.getPersone().get(4).addVoto();
                               // System.out.println(a); 	
                               
                                //out.writeObject(Operazione.Operazione_t.Op_ACK);
                                break;
                            case Inserisci:
                            	//this.rubrica.elimina((Persona) o);
                            	//out.writeObject(Operazione.Operazione_t.Op_ACK);
                            	out.writeInt(getTempoRestante());
                            	out.flush();
                                break;
                            case Ricerca:
                            	out.flush();
                            	//System.out.println(listacandidati);
                            	//listacandidati.getPersone().get(1).setVoti(1000);
                                out.writeObject(listacandidati);
                                out.flush();
                                break;
                            case Codice:	
                            	char[] options = {'Z','A','I','A','D','O','G','E','1','2','3','4'};
                        	    String result = "";
                        	    Random r = new Random();
                        	    for(int i=0;i<7;i++){
                        	        result = result + r.nextInt(options.length);
                        	    }
                        	    //System.out.println(result);
                                out.writeUTF(result);
                                out.flush();
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
