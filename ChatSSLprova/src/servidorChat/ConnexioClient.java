package servidorChat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import javax.net.ssl.SSLSocket;



public class ConnexioClient extends Thread implements Observer {
	
	private SSLSocket clientSocket;
	private MensajesChat mensajes;
	private DataInputStream entradaChat;
    private DataOutputStream sortidaChat;
    
	BufferedReader in = null;
	PrintStream out = null;
	
	public ConnexioClient (SSLSocket clientSocket, MensajesChat mensajes){
        this.clientSocket = clientSocket;
        this.mensajes = mensajes;
        
        try {
            entradaChat = new DataInputStream(clientSocket.getInputStream());
            sortidaChat = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
        	System.out.println(ex);
        	//Logger.getLogger(ConnexioClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	@Override
    public void run(){
        String mensajeRecibido;
        boolean conectado = true;
        // Se apunta a la lista de observadores de mensajes
        mensajes.addObserver(this);
        
        while (conectado) {
            try {
                // Lee un mensaje enviado por el cliente
                mensajeRecibido = entradaChat.readLine();
                // Pone el mensaje recibido en mensajes para que se notifique 
                // a sus observadores que hay un nuevo mensaje.
                mensajes.setMensaje(mensajeRecibido);
            } catch (IOException ex) {
            	System.out.println("Cliente con la IP " + clientSocket.getInetAddress().getHostName() + " desconectado.");
                //log.info("Cliente con la IP " + clientSocket.getInetAddress().getHostName() + " desconectado.");
                conectado = false; 
                // Si se ha producido un error al recibir datos del cliente se cierra la conexion con el.
                try {
                    in.close();
                    out.close();
                } catch (IOException ex2) {
                	System.out.println(ex2);
                	//Logger.getLogger(ConnexioClient.class.getName()).log(Level.SEVERE, null, ex2);
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
            //Logger.getLogger(ConnexioClient.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	
	
}
