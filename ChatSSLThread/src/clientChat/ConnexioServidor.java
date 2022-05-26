package clientChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.swing.JTextField;

public class ConnexioServidor implements ActionListener {
	@SuppressWarnings("unused")
	private SSLSocket socket;

	private JTextField tfMissatge;
	private String usuari;
	private DataOutputStream salidaDatos;

	public ConnexioServidor(SSLSocket socket, JTextField tfMissatge, String usuari) {
		this.socket = (SSLSocket) socket;
		this.tfMissatge = tfMissatge;
		this.usuari = usuari;

		System.setProperty("javax.net.ssl.trustStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "servpass");
		try {
			this.salidaDatos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException ex) {
			System.out.println("Error al crear el stream de sortida : " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.out.println("El socket no s'ha creat correctament. ");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			salidaDatos.writeUTF(usuari + ": " + metodes.CensuraParaules.paraulaCensura(tfMissatge.getText(), usuari));
			tfMissatge.setText("");
		} catch (IOException ex) {
			System.out.println("Error al intentar enviar un missatge: " + ex.getMessage());
		}
	}
}
