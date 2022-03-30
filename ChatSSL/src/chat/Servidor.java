package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


public class Servidor {
	static final int PORT = 9091;
	private boolean end = false;

	public static void main(String args[]) {
		Servidor server = new Servidor();
		System.out.println("Servidor escoltant...");
		server.listen();
	}

	public void listen() {
		SSLServerSocket serverSocket = null;
		SSLSocket clientSocket = null;

		System.setProperty("javax.net.ssl.keyStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "servpass");

		try {
			SSLServerSocketFactory servFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

			serverSocket = (SSLServerSocket) servFactory.createServerSocket(PORT);

			while (!end) {
				clientSocket = (SSLSocket) serverSocket.accept();
				// processem la petició del client
				proccesClientRequest(clientSocket);
				// tanquem el sòcol temporal per atendre el client
				closeClient(clientSocket);
			}
			// tanquem el sòcol principal
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}

		} catch (IOException ex) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void proccesClientRequest(Socket clientSocket) {
		boolean farewellMessage = false;
		String clientMessage = "";
		BufferedReader in = null;
		PrintStream out = null;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintStream(clientSocket.getOutputStream());
			do {
				// processem el missatge del client i generem la resposta. Si
				// clientMessage és buida generarem el missatge de benvinguda
				String dataToSend = processData(clientMessage);
				out.println(dataToSend);
				out.flush();
				clientMessage = in.readLine();
				farewellMessage = isFirewallMessage(clientMessage);
			} while ((clientMessage) != null && !farewellMessage);
		} catch (IOException ex) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void closeClient(Socket clientSocket) {
		// si falla el tancament no podem fer gaire cosa, només enregistrar
		// el problema
		try {
			// tancament de tots els recursos
			if (clientSocket != null && !clientSocket.isClosed()) {
				if (!clientSocket.isInputShutdown()) {
					clientSocket.shutdownInput();
				}
				if (!clientSocket.isOutputShutdown()) {
					clientSocket.shutdownOutput();
				}
				clientSocket.close();
			}
		} catch (IOException ex) {
			// enregistrem l’error amb un objecte Logger
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isFirewallMessage(String clientMessage) {
		if (clientMessage.equals("Fi"))
			return true;
		else
			return false;
	}

	public String processData(String clientMessage) {
		System.out.println(clientMessage);
		return clientMessage;
	}

}