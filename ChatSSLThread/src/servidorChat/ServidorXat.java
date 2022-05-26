package servidorChat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServidorXat {
	static final int PORT = 9000;
	static boolean end = false;

	public static void iniciarServidor() {
		System.setProperty("javax.net.ssl.keyStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "servpass");

		SSLServerSocket serverSocket = null;
		SSLSocket clientSocket = null;
		MissatgesXat mensajes = new MissatgesXat();

		try {
			SSLServerSocketFactory servFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			serverSocket = (SSLServerSocket) servFactory.createServerSocket(PORT);

			while (!end) {
				System.out.println("Servidor a la espera de connexions.");

				clientSocket = (SSLSocket) serverSocket.accept();

				System.out.println("client con la IP: " + clientSocket.getInetAddress().getHostName() + " conectado.");
				
				ConnexioClient cc = new ConnexioClient(clientSocket, mensajes);

				cc.start();
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}

		} catch (IOException ex) {
			Logger.getLogger(ServidorXat.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				serverSocket.close();
				clientSocket.close();
			} catch (IOException ex) {
				Logger.getLogger(ServidorXat.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
