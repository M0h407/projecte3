package clientChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.swing.JTextField;

public class ConexionServidor implements ActionListener {
	private SSLSocket socket;
	
	private JTextField tfMensaje;
    private String usuario;
    private DataOutputStream salidaDatos;
    
    public ConexionServidor(Socket socket, JTextField tfMensaje, String usuario) {
        this.socket = (SSLSocket) socket;
        this.tfMensaje = tfMensaje;
        this.usuario = usuario;
        
        System.setProperty("javax.net.ssl.trustStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "servpass");
        try {
            this.salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
        	System.out.println("Error al crear el stream de salida : " + ex.getMessage());
            //log.error("Error al crear el stream de salida : " + ex.getMessage());
        } catch (NullPointerException ex) {
        	System.out.println("El socket no se creo correctamente. ");
            //log.error("El socket no se creo correctamente. ");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            salidaDatos.writeUTF(usuario + ": " + tfMensaje.getText() );
            tfMensaje.setText("");
        } catch (IOException ex) {
        	System.out.println("Error al intentar enviar un mensaje: " + ex.getMessage());
            //log.error("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
    }
}
