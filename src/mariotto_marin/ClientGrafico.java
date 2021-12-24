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
import javax.swing.JPanel;
import javax.swing.ImageIcon;

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
					//Thread t_cg = new Thread((Runnable) cg);
					//t_cg.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JFrame frame;
	private JComboBox comboBox;
	private Socket connessione;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int npersona = 0;
	
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
		comboBox.setBounds(10, 194, 255, 31);
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
		btnVota.setBounds(92, 236, 89, 23);
		btnVota.setVisible(true); 
		frame.getContentPane().add(btnVota);
		btnVota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					out.writeObject(Operazione.Operazione_t.Vota);
					int cvotato = comboBox.getSelectedIndex();
					Candidato c = (Candidato) comboBox.getSelectedItem();
					//System.out.println(c);
					out.writeObject((Candidato) comboBox.getSelectedItem());
					btnVota.setEnabled(false);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		JLabel lblTextCodiceUnivoco = new JLabel("Il suo codice univoco:");
		lblTextCodiceUnivoco.setFont(new Font("Sitka Text", Font.PLAIN, 15));
		lblTextCodiceUnivoco.setBounds(13, 96, 177, 20);
		frame.getContentPane().add(lblTextCodiceUnivoco);

		JLabel lblCodiceUnivoco = new JLabel("----");
		lblCodiceUnivoco.setBounds(199, 97, 47, 14);
		frame.getContentPane().add(lblCodiceUnivoco);
		
		JLabel lblTempoVotazioni = new JLabel("--:--");
		lblTempoVotazioni.setBounds(550, 34, 47, 14);
		frame.getContentPane().add(lblTempoVotazioni);
		
		JLabel lblNewLabel = new JLabel("Tempo votazioni");
		lblNewLabel.setFont(new Font("Sitka Text", Font.PLAIN, 12));
		lblNewLabel.setBounds(510, 14, 106, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnMostraVotazioni = new JButton("Mostra votazioni");
		btnMostraVotazioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connessione.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					
					Socket con = new Socket("127.0.0.1",5000); 
					out = new ObjectOutputStream(con.getOutputStream());
					in = new ObjectInputStream(con.getInputStream());
					out.flush();
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
						System.out.println(persona.toString());
					}
					btnMostraVotazioni.setEnabled(false);
				} catch (IOException | ClassNotFoundException exc) {
					exc.printStackTrace();
				}
			}
		});
		btnMostraVotazioni.setBounds(10, 326, 109, 23);
		frame.getContentPane().add(btnMostraVotazioni);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(ClientGrafico.class.getResource("/mariotto_marin/Zaiastan.jpg")));
		lblNewLabel_1.setBounds(0, 0, 638, 406);
		frame.getContentPane().add(lblNewLabel_1);
	}
}

//Zona codice in lavorazione
