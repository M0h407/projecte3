package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client extends Thread implements Observer{

	public static void main(String args[]) {
		Client client = new Client();
		System.out.println("Client...");
		client.connect("127.0.0.1", 9090);
	}

	public void connect(String address, int port) {
		String serverData;
		String request;
		boolean continueConnected = true;
		SSLSocket socket;
		BufferedReader in;
		PrintStream out;
		
		System.setProperty("javax.net.ssl.trustStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "servpass");
		
		try {
			SSLSocketFactory cliFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			// SSLSocketFactory = new Socket(InetAddress.getByName(address), port);
			socket = (SSLSocket) cliFactory.createSocket(InetAddress.getByName(address), port);
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());

			Scanner entrada = new Scanner(System.in);

			// el client atén el port fins que decideix finalitzar
			while (continueConnected) {
				serverData = in.readLine();

				if (serverData.equals("Fi"))
					request = getRequest(serverData);
				else {
					request = entrada.nextLine();
				}
				// processament de les dades rebudes i obtenció d’una nova petició
				request = entrada.nextLine();
				;
				// enviament de la petició
				out.println(request);// assegurem que acaba amb un final de línia
				out.flush(); // assegurem que s’envia
				// comprovem si la petició és un petició de finalització i en cas
				// que ho sigui ens preparem per sortir del bucle
				continueConnected = mustFinish(request);
			}
			entrada.close();
			close(socket);
		} catch (UnknownHostException ex) {
			System.out.println("Error de connexió. No existeix el host" + ex);
		} catch (IOException ex) {
			System.out.println("Error de connexió indefinit" + ex);
		}
	}

	private void close(Socket socket) {
		// si falla el tancament no podem fer gaire cosa, només enregistrar
		// el problema
		try {
			// tancament de tots els recursos
			if (socket != null && !socket.isClosed()) {
				if (!socket.isInputShutdown()) {
					socket.shutdownInput();
				}
				if (!socket.isOutputShutdown()) {
					socket.shutdownOutput();
				}
				socket.close();
			}
		} catch (IOException ex) {
			// enregistrem l’error amb un objecte Logger
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean mustFinish(String request) {
		if (request.equals("Fi"))
			return false;
		else
			return true;
	}

	public String getRequest(String serverData) {
		return serverData;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
