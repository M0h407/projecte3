package servidorChat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServidorChat {
	static final int PORT = 9000;
	static boolean end = false;

	public static void iniciarServidor() {
		System.setProperty("javax.net.ssl.keyStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "servpass");

		SSLServerSocket serverSocket = null;
		SSLSocket clientSocket = null;
		MensajesChat mensajes = new MensajesChat();

		try {
			SSLServerSocketFactory servFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			serverSocket = (SSLServerSocket) servFactory.createServerSocket(PORT);

			while (!end) {
				System.out.println("Servidor a la espera de connexions.");

				clientSocket = (SSLSocket) serverSocket.accept();

				System.out.println("client con la IP: " + clientSocket.getInetAddress().getHostName() + " conectado.");
				// processem la petició del client
				ConnexioClient cc = new ConnexioClient(clientSocket, mensajes);

				cc.start();
				// proccesClientRequest(clientSocket);
				// tanquem el sòcol temporal per atendre el client
				// closeClient(clientSocket);
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}

		} catch (IOException ex) {
			Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				serverSocket.close();
				clientSocket.close();
			} catch (IOException ex) {
				Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
