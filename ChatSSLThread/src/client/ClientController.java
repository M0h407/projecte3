package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientController {

	@FXML
	private Button btnLogin;

	@FXML
	private Label lblResposta;

	@FXML
	private TextField txtDni;

	@FXML
	private TextField txtPsw;

	@FXML
	void handleBtnLogin(ActionEvent event) {
		String dni = txtDni.getText();
		String psw = txtPsw.getText();

		if (dni == "" && psw == "") {
			lblResposta.setText("");
			lblResposta.setText("No has introduit dni ni contrasenya.");
		} else if (dni == "") {
			lblResposta.setText("");
			lblResposta.setText("No has introduit dni.");
		} else if (psw == "") {
			lblResposta.setText("");
			lblResposta.setText("No has introduit contrasenya.");
		} else if ((dni != "" && psw != "")) {
			boolean logInf = metodes.Login.login(dni, psw);
			if (logInf == true) {
				lblResposta.setText("");
				lblResposta.setText("Login correcte.");
				clientChat.VentanaConfiguracion.obtNom(dni);
				clientChat.ClientChat.main(null);

			} else {
				lblResposta.setText("");
				lblResposta.setText("Login incorrecte.");
			}
		}
	}

}
