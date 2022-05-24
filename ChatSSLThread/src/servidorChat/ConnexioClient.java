package servidorChat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import javax.net.ssl.SSLSocket;

@SuppressWarnings("deprecation")
public class ConnexioClient extends Thread implements Observer {

	private SSLSocket clientSocket;
	private MensajesChat mensajes;
	private DataInputStream entradaChat;
	private DataOutputStream sortidaChat;

	BufferedReader in = null;
	PrintStream out = null;

	public ConnexioClient(SSLSocket clientSocket, MensajesChat mensajes) {
		this.clientSocket = clientSocket;
		this.mensajes = mensajes;

		try {
			entradaChat = new DataInputStream(clientSocket.getInputStream());
			sortidaChat = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void run() {
		String mensajeRecibido;
		boolean conectado = true;
		// S'apunte a la llista d'observadors de missatges
		mensajes.addObserver(this);

		while (conectado) {
			try {
				// Llegeix un missage enviat per el client
				mensajeRecibido = entradaChat.readUTF();
				// Posar el missatge rebut a missatges per a que es notifiqui
				// als seus observadors que hi ha un nou missatge.
				mensajes.setMensaje(mensajeRecibido);
			} catch (IOException ex) {
				System.out
						.println("Cliente con la IP " + clientSocket.getInetAddress().getHostName() + " desconectado.");
				conectado = false;
				// Si s'ha produit un error al rebre dades del client es tanca la
				// connexio amb ell.
				try {
					in.close();
					out.close();
				} catch (IOException ex2) {
					System.out.println(ex2);
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		try {
			// Envia el mensaje al cliente
			sortidaChat.writeUTF(arg.toString());
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

}
