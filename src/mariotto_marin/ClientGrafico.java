package mariotto_marin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextPane;

public class ClientGrafico {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGrafico cg = new ClientGrafico("127.0.0.1", 50000);
					cg.frame.setVisible(true);
					// Thread t_cg = new Thread((Runnable) cg);
					// t_cg.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;
	private JComboBox comboBox;
	private JTextPane textLog;
	private JLabel lblTempoVotazioni;
	private Socket connessione;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int npersona = 0;
	public String votazioni = "";

	/**
	 * @wbp.parser.entryPoint
	 */
	public ClientGrafico(String ip, int port) throws UnknownHostException, IOException {
		this.connessione = new Socket(InetAddress.getByName(ip), port);
		this.out = new ObjectOutputStream(this.connessione.getOutputStream());
		this.in = new ObjectInputStream(this.connessione.getInputStream());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 652, 443);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 162, 255, 31);
		frame.getContentPane().add(comboBox);
		try {
			this.out.writeObject(Operazione.Operazione_t.Ricerca);
			this.out.writeObject(new Candidato(null, null, null, 0));
			ListaCandidati res = (ListaCandidati) this.in.readObject();
			Iterator<Candidato> it = res.getPersone().iterator();
			while (it.hasNext()) {
				Candidato persona = it.next();
				//comboBox.addItem(persona.getCognome()+" "+persona.getNome()+" | "+persona.getPartito());
				comboBox.addItem(persona);
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		JButton btnVota = new JButton("Vota");
		btnVota.setBackground(new Color(255, 215, 0));
		btnVota.setBounds(90, 204, 89, 23);
		btnVota.setVisible(true); 
		frame.getContentPane().add(btnVota);
		btnVota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					out.writeObject(Operazione.Operazione_t.Vota);
					int cvotato = comboBox.getSelectedIndex();
					Candidato c = (Candidato) comboBox.getSelectedItem();
					textLog.setText("Hai votato: "+c);
					out.writeObject((Candidato) comboBox.getSelectedItem());
					btnVota.setEnabled(false);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		String codice = "";
		try {	
			this.out.writeObject(Operazione.Operazione_t.Codice);
			this.out.writeObject(new Candidato(null, null, null, 0));
			codice = this.in.readUTF(); 
			System.out.println(codice);
		} catch (IOException exce) {
			exce.printStackTrace();
		}

		JLabel lblTextCodiceUnivoco = new JLabel("Il suo codice univoco:");
		lblTextCodiceUnivoco.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		lblTextCodiceUnivoco.setBounds(13, 96, 177, 20);
		frame.getContentPane().add(lblTextCodiceUnivoco);

		JLabel lblCodiceUnivoco = new JLabel("----");
		lblCodiceUnivoco.setText(codice);
		lblCodiceUnivoco.setFont(new Font("Sitka Text", Font.BOLD, 14));
		lblCodiceUnivoco.setBounds(170, 99, 116, 14);
		frame.getContentPane().add(lblCodiceUnivoco);
		
		lblTempoVotazioni = new JLabel("--:--");
		lblTempoVotazioni.setFont(new Font("Sitka Text", Font.BOLD, 18));
		lblTempoVotazioni.setBounds(539, 44, 47, 14);
		frame.getContentPane().add(lblTempoVotazioni);
		
		JLabel lblNewLabel = new JLabel("Tempo rimanente");
		lblNewLabel.setFont(new Font("Sitka Text", Font.PLAIN, 16));
		lblNewLabel.setBounds(480, 14, 134, 21);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblScegliVota = new JLabel("Seleziona qui sotto il tuo candidato:");
		lblScegliVota.setBackground(Color.BLACK);
		lblScegliVota.setFont(new Font("Sitka Text", Font.BOLD, 14));
		lblScegliVota.setBounds(12, 141, 252, 18);
		frame.getContentPane().add(lblScegliVota);
		
		textLog = new JTextPane();
		textLog.setEditable(false);
		textLog.setBounds(14, 258, 256, 127);
		frame.getContentPane().add(textLog);
		
		JButton btnStatoVotazioni = new JButton("Stato votazioni");
		btnStatoVotazioni.setBackground(new Color(240, 128, 128));
		btnStatoVotazioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connessione.close();
					Socket connessione = new Socket("127.0.0.1",50000); 
					out = new ObjectOutputStream(connessione.getOutputStream());
					in = new ObjectInputStream(connessione.getInputStream());
					//out.flush();
					out.writeObject(Operazione.Operazione_t.Ricerca);
					out.writeObject(new Candidato(null, null, null, 0));
					Object o = in.readObject();
					//System.out.println(o instanceof ListaCandidati);
					ListaCandidati res = (ListaCandidati) o;
					//System.out.println(res);
					//System.out.print(res.getPersone().get(0).getVoti());
					Iterator<Candidato> it = res.getPersone().iterator();
					
					while (it.hasNext()) {
						Candidato persona = it.next();
						
						//System.out.println(persona.toStringVotazioni());
						votazioni += persona.toStringVotazioni()+  " \n";
					}
					textLog.setText(votazioni);
				} catch (IOException | ClassNotFoundException exc) {
					exc.printStackTrace();
				}
				btnStatoVotazioni.setEnabled(false);
			}
		});
		btnStatoVotazioni.setBounds(383, 281, 123, 23);
		frame.getContentPane().add(btnStatoVotazioni);

		int serverTime = 10;
		
		class Helper extends TimerTask{
		    public int i = 0;
		    public void run()
		    {
		    	if(i<=serverTime) {
		    	int tempo = serverTime-i;
		    	String time = Integer.toString(tempo);
		    	lblTempoVotazioni.setText(time);
		    	i++;
		        //System.out.println("Timer ran " + ++i);
		    	} else {
		    		//textLog.setText("Scaduto il tempo per le votazioni!");
		    		try {
						connessione.close();
						Socket connessione = new Socket("127.0.0.1",50000); 
						out = new ObjectOutputStream(connessione.getOutputStream());
						in = new ObjectInputStream(connessione.getInputStream());
						//out.flush();
						out.writeObject(Operazione.Operazione_t.Ricerca);
						out.writeObject(new Candidato(null, null, null, 0));
						Object o = in.readObject();
						//System.out.println(o instanceof ListaCandidati);
						ListaCandidati res = (ListaCandidati) o;
						//System.out.println(res);
						//System.out.print(res.getPersone().get(0).getVoti());
						Iterator<Candidato> it = res.getPersone().iterator();
						
						while (it.hasNext()) {
							Candidato persona = it.next();
							
							//System.out.println(persona.toStringVotazioni());
							votazioni += persona.toStringVotazioni()+  " \n";
						}
						textLog.setText(votazioni);
					} catch (IOException | ClassNotFoundException exc) {
						exc.printStackTrace();
					}
		    		btnVota.setEnabled(false);
		    	}
		    }
		}
		
		Timer timer = new Timer();
		TimerTask task = new Helper();
		timer.schedule(task, 0, 1000);
		
		JLabel lblSfondo = new JLabel("");
		lblSfondo.setIcon(new ImageIcon(ClientGrafico.class.getResource("/mariotto_marin/Zaiastan.jpg")));
		lblSfondo.setBounds(0, 0, 638, 406);
		frame.getContentPane().add(lblSfondo);
		
	}
}

//Zona codice in lavorazione
