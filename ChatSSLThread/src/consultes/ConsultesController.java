package consultes;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConsultesController {

	ObservableList<String> mainCategoriesList = FXCollections.observableArrayList("Sexual", "Terrorista",
			"Paraules malsonants");

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnDesencriptar;

	@FXML
	private Button btnInserir;

	@FXML
	private Label lblResposta1;

	@FXML
	private Label lblResposta2;

	@FXML
	private TextField txtDniDes;

	@FXML
	private TextField txtNumDes;

	@FXML
	private TextField txtParaula;

	@FXML
	private TextField txtResposta;

	@FXML
	private ComboBox<String> mainCategories;

	@FXML
	private void initialize() {
		mainCategories.setValue("Categoria");
		mainCategories.setItems(mainCategoriesList);
	}

	@FXML
	void handleBtnBuscar(ActionEvent event) {
		String paraula = txtResposta.getText();
		boolean insultExist = metodes.ConsultesInsults.conInsultExist(paraula);
		if (insultExist == true) {
			lblResposta1.setText("");
			lblResposta1.setText("La paraula està a la base de dades");
		} else {
			lblResposta1.setText("");
			lblResposta1.setText("La paraula no està a la base de dades");
		}
	}

	@FXML
	void handleBtnInserir(ActionEvent event) {
		String paraula = txtParaula.getText();
		String getValueComboBox = mainCategories.getValue().toString();
		if (getValueComboBox != "Categoria") {
			if (getValueComboBox == "Paraules malsonants") {
				metodes.ConsultesInsults.inserirNouInsults(paraula, "paraulesMalsonants");
			} else {
				metodes.ConsultesInsults.inserirNouInsults(paraula, getValueComboBox);
			}
			lblResposta2.setText("");
			lblResposta2.setText("Insercio Correcta");
		} else {
			lblResposta2.setText("");
			lblResposta2.setText("Selecciona una categoria");
		}
	}

	@FXML
	void handleBtnDesencriptar(ActionEvent event) throws IOException {
		String dni = txtDniDes.getText();
		String arxSel = txtNumDes.getText();
		if(dni != "" && arxSel != "") {
			System.out.println("be");
			xifrarFitxers.xifraFitx.obtenirNomXml(dni, arxSel);
		} else {
			System.out.println("caca");
		}
		
	}

}
