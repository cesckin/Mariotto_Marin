package mariotto_marin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGrafico {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGrafico window = new ClientGrafico();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGrafico() {
		initialize();
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
