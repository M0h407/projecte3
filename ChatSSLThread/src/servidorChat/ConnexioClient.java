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
	private MissatgesXat missatges;
	private DataInputStream entradaXat;
	private DataOutputStream sortidaXat;

	BufferedReader in = null;
	PrintStream out = null;

	public ConnexioClient(SSLSocket clientSocket, MissatgesXat missatges) {
		this.clientSocket = clientSocket;
		this.missatges = missatges;

		try {
			entradaXat = new DataInputStream(clientSocket.getInputStream());
			sortidaXat = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void run() {
		String mensajeRecibido;
		boolean conectado = true;
		missatges.addObserver(this);
		while (conectado) {
			try {
				mensajeRecibido = entradaXat.readUTF();
				missatges.setMensaje(mensajeRecibido);
			} catch (IOException ex) {
				System.out
						.println("Client amb la IP " + clientSocket.getInetAddress().getHostName() + " desconnectat.");
				conectado = false;
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
			sortidaXat.writeUTF(arg.toString());
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

}
