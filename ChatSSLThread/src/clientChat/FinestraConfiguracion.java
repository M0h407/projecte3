package clientChat;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class FinestraConfiguracion extends JDialog {
	public static String name = "";
	
	private JTextField tfUsuari;
	private JTextField tfHost;
	private JTextField tfPort;

	public FinestraConfiguracion(JFrame padre) {
		super(padre, "Configuració inicial", true);

		JLabel lbUsuari = new JLabel("Usuari:");
		JLabel lbHost = new JLabel("Host:");
		JLabel lbPort = new JLabel("Port:");

		tfUsuari = new JTextField(name);
		tfHost = new JTextField("localhost");
		tfPort = new JTextField("9000");

		JButton btAcceptar = new JButton("Acceptar");
		btAcceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		Container c = this.getContentPane();
		c.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(20, 20, 0, 20);

		gbc.gridx = 0;
		gbc.gridy = 0;
		c.add(lbUsuari, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		c.add(lbHost, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		c.add(lbPort, gbc);

		gbc.ipadx = 100;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 1;
		gbc.gridy = 0;
		c.add(tfUsuari, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		c.add(tfHost, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		c.add(tfPort, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 20, 20, 20);
		c.add(btAcceptar, gbc);

		this.pack(); 
		this.setLocation(450, 200); 
		this.setResizable(false); 
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); 
		this.setVisible(true);
	}

	public String getUsuario() {
		return this.tfUsuari.getText();
	}

	public String getHost() {
		return this.tfHost.getText();
	}

	public int getPuerto() {
		return Integer.parseInt(this.tfPort.getText());
	}
	
	public static void obtNom(String dni) {
		name = metodes.CensuraParaules.obtenirUser(dni);
	}
}
