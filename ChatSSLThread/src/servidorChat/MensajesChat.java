package servidorChat;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class MensajesChat extends Observable {
	private String mensaje;

	public MensajesChat() {
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
		// Indica que el missatge s'ha cambiat
		this.setChanged();
		// Notifica als observadors que el minssatge ha cambiat
		this.notifyObservers(this.getMensaje());
	}
}
