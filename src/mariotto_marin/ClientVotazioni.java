package mariotto_marin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;

public class ClientVotazioni implements Runnable {

    public static void main(String[] args) {
        try {
            ClientVotazioni cr = new ClientVotazioni("127.0.0.1", 5000);
            Thread t_cr = new Thread(cr);

            t_cr.start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket connessione;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int npersona=0; 

    public ClientVotazioni(String ip, int port) throws IOException {
        this.connessione = new Socket(InetAddress.getByName(ip), port);
        this.out = new ObjectOutputStream(this.connessione.getOutputStream());
        this.in = new ObjectInputStream(this.connessione.getInputStream());
    }

    @Override
    public void run() {
    	int scelta = 0;
    	do {
    		scelta=0;
	    	System.out.println("1: Visualizza lista candidati\n2: Vota un candidato\nCosa desideri fare?\n");
	    	Scanner scanner = new Scanner(System.in);
	        scelta = scanner.nextInt();
	        Scanner scannerS = new Scanner(System.in);
	        switch(scelta) {
	        
		        case 1:{
		        	try {
		        	this.out.writeObject(Operazione.Operazione_t.Ricerca);
		            this.out.writeObject(new Candidato(null, null, null, 0));
		            ListaCandidati res = (ListaCandidati) this.in.readObject();
		            Iterator<Candidato> it = res.getPersone().iterator();
		            while (it.hasNext()) {
		            	Candidato persona = it.next();
		                System.out.println(persona);
		            }
		        	} catch (IOException | ClassNotFoundException e) {
			            e.printStackTrace();
			        }
		        	break;
		        }
		        case 2:{
		        	try {
						this.out.writeObject(Operazione.Operazione_t.Vota);
						System.out.println("Quale candidato desideri votare (0-4)");
						scanner = new Scanner(System.in);
				        int cvotato = scanner.nextInt();
						this.out.writeObject(new Candidato(null, null, null, cvotato));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	break;
		        	
		        }
		        case 3:{
		        	
		        }
		        case 4:{
		        	break;
		        }
	        }
	    	
	    	/*
	        Persona p1 = new Persona("p1", "p1", "planck", 123456);
	        Persona p2 = new Persona("p2", "p2", "planck", 1234567);
	        Persona nuova = new Persona("Mario", "Draghi", "Agrolife", 3);  
	        try {
	            this.out.writeObject(Operazione.Operazione_t.Aggiungi);
	            this.out.writeObject(p1);
	            if (this.in.readObject() == Operazione.Operazione_t.Op_ACK) {
	                System.out.println("p1 aggiunto correttamente");
	            }
	
	            this.out.writeObject(Operazione.Operazione_t.Aggiungi);
	            this.out.writeObject(p2);
	            if (this.in.readObject() == Operazione.Operazione_t.Op_ACK) {
	                System.out.println("p2 aggiunto correttamente");
	            }
	
	            this.out.writeObject(Operazione.Operazione_t.Ricerca);
	            this.out.writeObject(new Persona(null, null, null, 0));
	            Rubrica res = (Rubrica) this.in.readObject();
	            Iterator<Persona> it = res.getPersone().iterator();
	            while (it.hasNext()) {
	                Persona persona = it.next();
	                System.out.println(persona);
	            }
	            
	         // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	            this.out.writeObject(Operazione.Operazione_t.Modifica);
	            this.out.writeObject(p1);
	            if (this.in.readObject() == Operazione.Operazione_t.Op_ACK) {
	                System.out.println("p1 modificato correttamente");
	            }
	
	            
	            // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	            this.out.writeObject(Operazione.Operazione_t.Elimina);
	            this.out.writeObject(p1);
	            if (this.in.readObject() == Operazione.Operazione_t.Op_ACK) {
	                System.out.println("p1 rimosso correttamente");
	            }
	
	            this.out.writeObject(Operazione.Operazione_t.Elimina);
	            this.out.writeObject(p2);
	            if (this.in.readObject() == Operazione.Operazione_t.Op_ACK) {
	                System.out.println("p2 rimosso correttamente");
	            }

	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        */
    	}while(scelta!=0);
    }	
}
