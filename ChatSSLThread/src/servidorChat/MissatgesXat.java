package servidorChat;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class MissatgesXat extends Observable {
	private String missatge;

	public MissatgesXat() {
	}

	public String getMissatge() {
		return missatge;
	}

	public void setMensaje(String missatge) {
		this.missatge = missatge;
		this.setChanged();
		this.notifyObservers(this.getMissatge());
	}
}
