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

public class ClientGrafico {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGrafico cg = new ClientGrafico("127.0.0.1", 5000);
					cg.frame.setVisible(true);
		            Thread t_cg = new Thread((Runnable) cg);
		            t_cg.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JFrame frame;
	private Socket connessione;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int npersona=0; 

	public ClientGrafico(String ip, int port) throws UnknownHostException, IOException {
		this.connessione = new Socket(InetAddress.getByName(ip), port);
        this.out = new ObjectOutputStream(this.connessione.getOutputStream());
        this.in = new ObjectInputStream(this.connessione.getInputStream());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 307, 397);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnVota = new JButton("Vota");
		btnVota.setBounds(96, 286, 89, 23);
		frame.getContentPane().add(btnVota);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(79, 88, 125, 155);
		frame.getContentPane().add(comboBox);
		try {
			this.out.writeObject(Operazione.Operazione_t.Ricerca);
			this.out.writeObject(new Candidato(null, null, null, 0));
			ListaCandidati res = (ListaCandidati) this.in.readObject();
			Iterator<Candidato> it = res.getPersone().iterator();
			while (it.hasNext()) {
				Candidato persona = it.next();
				JOptionPane.showMessageDialog(null, persona);
				comboBox.addItem(persona);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		JLabel lblBenvenuto = new JLabel("Benvenuto elettore!");
		lblBenvenuto.setFont(new Font("Source Code Pro Medium", Font.BOLD, 14));
		lblBenvenuto.setBounds(10, 11, 171, 18);
		frame.getContentPane().add(lblBenvenuto);

		JLabel lblTextCodiceUnivoco = new JLabel("Il suo codice univoco:");
		lblTextCodiceUnivoco.setBounds(10, 34, 130, 14);
		frame.getContentPane().add(lblTextCodiceUnivoco);

		JLabel lblCodiceUnivoco = new JLabel("*****");
		lblCodiceUnivoco.setBounds(119, 34, 47, 14);
		frame.getContentPane().add(lblCodiceUnivoco);
	}
}

//Zona codice in lavorazione
