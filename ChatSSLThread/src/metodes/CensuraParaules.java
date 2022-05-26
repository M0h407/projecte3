package metodes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.Connect;

public class CensuraParaules {
	static ArrayList<String> insults = new ArrayList<>();

	public static String paraulaCensura(String frase, String nom) throws IOException {
		String dni = obtDni(nom);
		String data = metodes.GenerarXML.dataHora();

		String[] fraseSplit = frase.split(" ");

		boolean estat = false;
		String fraseFin = "";
		String paraulaCens = "";
		String paraula = "";

		int numParaules = fraseSplit.length;
		for (int i = 0; i < numParaules; i++) {
			paraula = fraseSplit[i];
			boolean existe = insults.contains(paraula);
			if (existe == true) {
				char[] l = paraula.toCharArray();
				int num = l.length - 1;
				char parFin = l[0];
				paraulaCens = parFin + "";
				for (int e = 0; e < num; e++) {
					paraulaCens += "*";
				}
				metodes.ConsultesInsults.conInsult(paraula, dni);
				metodes.mongoDB.inserirParaulaInsult(paraula, nom, data, dni);
				metodes.GenerarXML.generarXML(dni, paraulaCens, nom, paraula);
				estat = true;
			} else {
				paraulaCens = paraula;
			}
			fraseFin += paraulaCens + " ";
		}
		if (estat == true) {
			metodes.mongoDB.inserirInsult(fraseFin, nom, data, dni);
		}
		return fraseFin;
	}

	public static void carregarInsults() {
		Connection conn = Connect.obtenir_connexio_BD();
		String getInsult = "SELECT * FROM insults";
		try {
			Statement stmtDep;
			ResultSet rs;

			stmtDep = conn.createStatement();
			rs = stmtDep.executeQuery(getInsult);
			while (rs.next()) {
				String ins = rs.getString("nom");
				insults.add(ins);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String obtDni(String nom) {
		String dni = "";
		Connection conn = Connect.obtenir_connexio_BD();
		String getInsult = "SELECT dni FROM persones WHERE nom = '" + nom + "';";
		try {
			Statement stmtDep;
			ResultSet rs;

			stmtDep = conn.createStatement();
			rs = stmtDep.executeQuery(getInsult);
			if (rs.next()) {
				dni = rs.getString("dni");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dni;
	}

	public static String obtenirUser(String dni) {
		String nom = "";
		Connection conn = Connect.obtenir_connexio_BD();
		String getUsr = "SELECT nom FROM persones WHERE dni ='" + dni + "';";

		try {
			Statement stmtDep;
			ResultSet rs;

			stmtDep = conn.createStatement();
			rs = stmtDep.executeQuery(getUsr);
			while (rs.next()) {
				nom = rs.getString("nom");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nom;
	}
}
