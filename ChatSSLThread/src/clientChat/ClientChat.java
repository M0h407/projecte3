package clientChat;

import java.awt.Container;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ClientChat extends JFrame {
	private JTextArea mensajesChat;
	private SSLSocket socket;

	private int PORT;
	private String host;
	private String usuario;

	public ClientChat() {
		super("Cliente Chat");

		// Elementos de la ventana
		mensajesChat = new JTextArea();
		mensajesChat.setEnabled(false); // El area de mensajes del chat no se debe de poder editar
		mensajesChat.setLineWrap(true); // Las lineas se parten al llegar al ancho del textArea
		mensajesChat.setWrapStyleWord(true); // Las lineas se parten entre palabras (por los espacios blancos)
		JScrollPane scrollMensajesChat = new JScrollPane(mensajesChat);
		JTextField tfMensaje = new JTextField("");
		JButton btEnviar = new JButton("Enviar");

		// Colocacion de los componentes en la ventana
		Container c = this.getContentPane();
		c.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(20, 20, 20, 20);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		c.add(scrollMensajesChat, gbc);
		// Restaura valores por defecto
		gbc.gridwidth = 1;
		gbc.weighty = 0;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 20, 20, 20);

		gbc.gridx = 0;
		gbc.gridy = 1;
		c.add(tfMensaje, gbc);
		// Restaura valores por defecto
		gbc.weightx = 0;

		gbc.gridx = 1;
		gbc.gridy = 1;
		c.add(btEnviar, gbc);

		this.setBounds(400, 100, 400, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ventana de configuracion inicial
		VentanaConfiguracion vc = new VentanaConfiguracion(this);
		host = vc.getHost();
		PORT = vc.getPuerto();
		usuario = vc.getUsuario();

		System.out.println("Quieres conectarte a " + host + " en el puerto " + PORT + " con el nombre de ususario: "
				+ usuario + ".");

		// Es crea el socket SSl per a utilitzarlo despres
		System.setProperty("javax.net.ssl.trustStore", "serverKey.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "servpass");

		try {

			SSLSocketFactory cliFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			// SSLSocketFactory = new Socket(InetAddress.getByName(address), port);
			socket = (SSLSocket) cliFactory.createSocket(InetAddress.getByName(host), PORT);
			// socket = (SSLSocket) new Socket(host, PORT);
		} catch (UnknownHostException ex) {
			System.out.println("No s'ha pogut connectar amb el servidor (" + ex.getMessage() + ").");
		} catch (IOException ex) {
			System.out.println("No s'ha pogut connectar amb el servidor (" + ex.getMessage() + ").");
		}

		// Accion para el boton enviar
		btEnviar.addActionListener(new ConexionServidor(socket, tfMensaje, usuario));

	}

	/**
	 * Recibe los mensajes del chat reenviados por el servidor
	 */

	public void recibirMensajesServidor() {
		// Obtenir el fluxe de entrada del socket
		DataInputStream entradaDatos = null;
		String mensaje;
		try {
			entradaDatos = new DataInputStream(socket.getInputStream());
		} catch (IOException ex) {
			System.out.println("Error al crear el stream d'entrada: " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.out.println("El socket no s'ha creat correctament. ");
		}

		// Bucle infinit que rep missatges del servidor
		boolean conectado = true;
		while (conectado) {
			try {
				mensaje = entradaDatos.readUTF();
				mensajesChat.append(mensaje + System.lineSeparator());
			} catch (IOException ex) {
				System.out.println("Error al llegir el stream d'entrada: " + ex.getMessage());
				conectado = false;
			} catch (NullPointerException ex) {
				System.out.println("El socket no s'ha creat correctament. ");
				conectado = false;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */

	public static void main(String[] args) {
		metodes.CensuraParaules.carregarInsults();
		ClientChat c = new ClientChat();
		c.recibirMensajesServidor();
	}
}
